package canonical.equation;

import java.util.*;
import java.util.Map.*;

public class Canonicalizer {
		
	private Map<String,Double> allTerms;
	private String canoEquation;
	public static final String CONSTANT_TERM = "CONSTANT";
		
	
	/**
	 * Return the canonical representation of a given equation
	 */
	public String getCanoEquation(String str){
		
		str = str.replaceAll("\\s", "");
		allTerms = collectTerms(str);
		if(allTerms.isEmpty()){
			throw new InvalidEquationException();
		}
		canoEquation = buildCanoEquation(allTerms);
		return canoEquation;
	}
	
	
	/**
	 * @param str  the equation to be canonicalize
	 * @return 	   a map that contains all the terms and coefficients 
	 * @throws InvalidEquationException if there is not exactly a left equation and a right equation
	 */
	private Map<String,Double> collectTerms(String str) {
		String[] equations = str.split("=");
		if(equations.length != 2 || equations[0].length() == 0 || equations[1].length() == 0){
			throw new InvalidEquationException();
		}
		String left = equations[0];
		String right = equations[1];
		Map<String,Double> result = new TreeMap<String,Double>();

		parseEquationtoMap(left,false,result);
		parseEquationtoMap(right,true,result);
		
		return result;
	}
	
	
	/**
	 * @param offset	index of '('
	 * @param sequence	the string containing bracket
	 * @return 			the string inside the bracket, nested brackets are included.
	 */
	private String buildBracketInnerString(int offset, String sequence){
		StringBuilder sb = new StringBuilder();
		int bracketCount = 0;	
		do{
			if(sequence.charAt(offset) == '('){
				bracketCount ++;
			}else if (sequence.charAt(offset) == ')'){
				bracketCount --;
			}
			sb.append(sequence.charAt(offset));
			offset++;
		} while (offset < sequence.length() && bracketCount > 0);
		
		if(bracketCount != 0){
			throw new InvalidEquationException();
		}

		return sb.toString().substring(1, sb.length()-1);
	}
	
	
	/**
	 * Parse the input string into individual summands and add them to a map<term,coefficient>
	 * @param sequence  		the equation string
	 * @param invertOperation	indicate whether '-' should be treated as '+' and vice versa
	 * @param map				the <term,coefficient> pairs after parsing the entire equation
	 * @throws InvalidEquationException 
	 */
	private void parseEquationtoMap(String sequence, boolean invertOperation, Map<String,Double> result){
		int offset = 0;
		while (offset < sequence.length()){
			Operator op = null;
			Double coeff = null;
			String term = CONSTANT_TERM;
			char current = sequence.charAt(offset);

			op = Parser.parseOperator(current,invertOperation);
			offset += op.getOperatorLength();
				
			if(offset < sequence.length()){
				if(sequence.charAt(offset) == '('){
					String str = buildBracketInnerString(offset,sequence);
					offset += str.length() + 2;
					if(op == Operator.ADD || (op == Operator.BLANK  && !invertOperation)){
						parseEquationtoMap(str,false,result);
					}else{
						parseEquationtoMap(str,true,result);
					}
					continue;
				}
			}
			
			try {
				if(offset < sequence.length()){
					coeff = Parser.parseNextDouble(sequence,offset);
					if(coeff != null){
						offset += isInteger(coeff) ? Double.toString(coeff).length() - 2 : Double.toString(coeff).length();
					}
				}
			} catch (NumberFormatException ex) {
				/* Coefficient 1 is invisible in the equation, handle the exception by assigning 1 to the coefficient */
				coeff = 1.0;
			}

			if(offset < sequence.length()){
				term = Parser.parseNextTerm(sequence, offset);
				if(term != null){
					offset += isConstantTerm(term) ? 0 : term.length();
				}
			}			
			
//			System.out.println(op+" Coeff " + coeff + " and Term " + term);
			
			/* Add <term,coeff> to result */
			if(op != null && coeff != null && term != null){
				if(result.containsKey(term)){
					if(op == Operator.ADD || (op == Operator.BLANK && !invertOperation)){
						result.put(term, result.get(term) + coeff);
					}else{
						result.put(term, result.get(term) - coeff);
					}
				}else{
					if(op == Operator.ADD || (op == Operator.BLANK && !invertOperation)){
						result.put(term, coeff);
					}else{
						result.put(term, -coeff);
					}
				}		
			}else{
				throw new InvalidEquationException();
			}
		}
	}
	
	
	/**
	 * @param map	the <term,coefficient> pairs after parsing the entire equation
	 * @return 	  	the string representation of all <term,coefficient> pair in canonical form.
	 */
	private String buildCanoEquation(Map<String,Double> map){
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, Double>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,Double> pair = (Map.Entry<String,Double>)it.next();
			String term = pair.getKey();
			Double coeff = pair.getValue();	
	
			/* Skip 0 coefficient term and constant term */
			if(coeff == 0 || isConstantTerm(term)) continue;
			
			/* Append + signs for all positive coefficient except for first term*/
			if(coeff > 0 && sb.length() != 0) sb.append("+");	
			
			/* Get Integer value for Double if possible, except for terms with coefficient 1 */
			if (isInteger(coeff)) {
				int intCoeff = coeff.intValue();
				sb.append(intCoeff);
				if(Math.abs(intCoeff) == 1){
					sb.deleteCharAt(sb.length()-1);
				}
			}else{
				sb.append(coeff);
			}
			sb.append(term);
		}
		
		/* Append constant term at the end */
		if(map.containsKey(CONSTANT_TERM) && map.get(CONSTANT_TERM) != 0){
			if(map.get(CONSTANT_TERM) > 0){
				sb.append("+");
			}
			sb.append(map.get(CONSTANT_TERM).intValue());
		}
		
		/* Append 0 if all terms canceled out */
		if(sb.length() == 0) sb.append("0");
		sb.append("=0");
		return sb.toString();
	}
	
	
	/**
	 * Return true if double equals its integer form 
	 */
	private boolean isInteger(Double d){
		return (d == Math.floor(d)) && !Double.isInfinite(d);
	}
	
	
	private boolean isConstantTerm(String s){
		return s == CONSTANT_TERM ? true : false;
	}
}
