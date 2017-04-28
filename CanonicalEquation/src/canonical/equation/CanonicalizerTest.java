package canonical.equation;

import static org.junit.Assert.*;

import org.junit.Test;

public class CanonicalizerTest {

	@Test
	public void testGetCanoEquation1(){
		Canonicalizer cano = new Canonicalizer();
		assertEquals("x^2+4.5xy-y^2+1=0",cano.getCanoEquation("x^2+3.5xy+y+1=y^2-xy+y"));
	}
	
	@Test
	public void testGetCanoEquation2(){
		Canonicalizer cano = new Canonicalizer();
		assertEquals("x-6=0",cano.getCanoEquation("x=6"));
	}
	
	@Test
	public void testSingleBracketOnLeft1(){
		Canonicalizer cano = new Canonicalizer();
		assertEquals("2x-y^2=0",cano.getCanoEquation("x-(y^2-x)=0"));
	}

	@Test
	public void testSingleBracketOnLeft2(){
		Canonicalizer cano = new Canonicalizer();
		assertEquals("4x-y-7=0",cano.getCanoEquation("(4x-y)-1=6"));
	}	
	
	@Test
	public void testSingleBracketOnRight(){
		Canonicalizer cano = new Canonicalizer();
		assertEquals("4x-3y-6=0",cano.getCanoEquation("4x=(6+3y)"));
	}
	
	@Test
	public void testNestedBracketOnRight(){
		Canonicalizer cano = new Canonicalizer();
		assertEquals("4x-3y-14=0",cano.getCanoEquation("4x-(3y+4)=10"));
	}
	
	@Test
	public void testNestedBracketOnBoth(){
		Canonicalizer cano = new Canonicalizer();
		assertEquals("4x+5xy-3y-14=0",cano.getCanoEquation("4x-(3y+4)=(10-5xy)"));
	}
	
	@Test
	public void testNestedBracketOnLeft(){
		Canonicalizer cano = new Canonicalizer();
		assertEquals("2x-y^2-1=0",cano.getCanoEquation("(x-(y^2-x))-1=0"));
	}			
			
	@Test
	public void testAllTermsCancelOutEquation(){
		Canonicalizer cano = new Canonicalizer();
		assertEquals("0=0",cano.getCanoEquation("x-(0-(0-x))=0"));
	}
	
	@Test (expected=InvalidEquationException.class)
	public void testNoEqualSignEquantion(){
		Canonicalizer cano = new Canonicalizer();
		cano.getCanoEquation("x-1");
	}
	
	@Test (expected=InvalidEquationException.class)
	public void testInvalidBracketEquation(){
		Canonicalizer cano = new Canonicalizer();
		cano.getCanoEquation("((4x-y)-1=6");
	}	
	
	@Test (expected=InvalidEquationException.class)
	public void testInvalidTerm(){
		Canonicalizer cano = new Canonicalizer();
		cano.getCanoEquation("4x^@-1=6");
	}	
	
	@Test (expected=InvalidEquationException.class)
	public void testInvalidCoeff(){
		Canonicalizer cano = new Canonicalizer();
		cano.getCanoEquation("4.24.3x-1=6");
	}

}
