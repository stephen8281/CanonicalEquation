package canonical.equation;

public class InvalidEquationException extends RuntimeException {

	public void printErrorMsg(){
		System.out.println("Invalid Equation!");
	}
}
