package canonical.equation;

public enum Operator {
	ADD("+"),SUBSTRACT("-"),BLANK("");
	
	private String symbol;
	
	private Operator(String str){
		symbol = str;
	}
	
	public int getOperatorLength(){
		return symbol.length();
	}
	
	public String getOperatorSymbol(){
		return symbol;
	}
	
}
