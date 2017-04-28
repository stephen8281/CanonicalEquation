package canonical.equation;

public class Parser {
	
	/**
	 * Return the next term in the equation
	 */
	public static String parseNextTerm(String sequence, int offset){
		StringBuilder sb = new StringBuilder();
		while (offset < sequence.length() && (Character.isLetter(sequence.charAt(offset)) || 
				Character.isDigit(sequence.charAt(offset)) || sequence.charAt(offset) == '^')){
			sb.append(sequence.charAt(offset));
			offset ++;
		}
		if(sb.length() == 0) {
			return Canonicalizer.CONSTANT_TERM;
		}
		return isValidTerm(sb.toString()) ? sb.toString() : null;
	}
	
	/**
	 * Determines whether a given string is a valid Term. e.g. x^3 is valid, y is valid
	 * @param str 
	 * @return true is str is a Double, false otherwise
	 */
	private static boolean isValidTerm(String str){
		if(str.matches("[a-zA-Z]+\\.?\\^\\d+") || str.matches("[a-zA-Z]+")){
			return true;
		}
		return false;
	}
	
	
	/**
	 * Return the next coefficient in the equation.
	 * @exception NumberFormatException is raised when trying to parse an invisible coefficient, which is 1.   
	 */
	public static Double parseNextDouble(String sequence, int offset){
		StringBuilder sb = new StringBuilder();
		while (offset < sequence.length() && (Character.isDigit(sequence.charAt(offset)) || sequence.charAt(offset) == '.') ){
			sb.append(sequence.charAt(offset));
			offset ++;
		}
		return isValidDouble(sb.toString()) ? Double.parseDouble(sb.toString()) : null;
	}
	
	/**
	 * Determines whether a given string is a valid Double; An empty string is valid because there can be 
	 * invisible coefficients  e.g. x+y=1 where x and y have coefficient 1.
	 * @param str 
	 * @return true is str is a Double, false otherwise
	 */
	private static boolean isValidDouble(String str){
		if(str.matches("\\d+\\.\\d+") || str.matches("\\d+") || str.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 * Return the Operator based on the input character.  Operator is reversed if invertOperation is set to true.
	 */	
	public static Operator parseOperator(char op, boolean invertOperation){
		if(invertOperation){
			switch(op){
				case '+' : return Operator.SUBSTRACT;
				case '-' : return Operator.ADD;
			}		
		}else{
			switch(op){
				case '+' : return Operator.ADD;
				case '-' : return Operator.SUBSTRACT;
			}			
		}
		return Operator.BLANK;
	}
}
