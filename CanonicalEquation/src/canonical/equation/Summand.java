package canonical.equation;

public class Summand {
	private String term;
	private int coefficient;
	private Operator operator;
	
	public Summand(String str, int coeff, Operator op){
		term = str;
		coefficient = coeff;
		operator = op;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(operator.getOperatorSymbol());
		sb.append(coefficient);
		sb.append(term);
		return sb.toString();
	}
	
}
