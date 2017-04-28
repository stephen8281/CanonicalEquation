package canonical.equation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Program {

	public static void main(String[] args) {

		if(args.length == 0 ){
	        System.out.println("Equation to Canonical form");
	        System.out.println("-----Press Ctrl + C to exit the program-----");
        	System.out.print("Enter equation: ");
        	Scanner sc = new Scanner(System.in);
        	while(sc.hasNext()){
        		String equation = sc.next();
        		if(equation == null || equation.length() == 0) continue;
	        	System.out.print("Canonical form: ");
	        	Canonicalizer cano = new Canonicalizer();
				try {
					String result = cano.getCanoEquation(equation);
	        		System.out.println(result);
				} catch (InvalidEquationException e) {
					e.printErrorMsg();
				}
        		System.out.println();
	        	System.out.print("Enter equation: ");
        	}
	        sc.close();
	        
		} else if (args.length == 1){
			System.out.println("File mode");
			String inputFileName = args[0];
	        String outputFileName = "output.txt";
	        String line = null;
	        try {
	            FileReader fileReader =  new FileReader(inputFileName);
	            BufferedReader bufferedReader = new BufferedReader(fileReader);

	            FileWriter fileWriter =  new FileWriter(outputFileName);
	            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

	        	Canonicalizer cano = new Canonicalizer();
	        	
	            while((line = bufferedReader.readLine()) != null) {
	                String result = cano.getCanoEquation(line);
	                bufferedWriter.write(result);
	                bufferedWriter.newLine();
	            }   
	            
	            bufferedReader.close();
	            bufferedWriter.close();
	            
	            System.out.println("-----File processing finished-----");
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println("Unable to open file '" + inputFileName + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println("Error reading file '" + inputFileName + "'");                  
	            ex.printStackTrace();
	        }
	        
		} else {
	        System.out.println("Please enter the command in the correct format");
	        System.out.println("File mode: java -jar trulioo.jar <file name>");
	        System.out.println("Interactive mode: java -jar trulioo.jar");
	        System.exit(0);
		}
	}
}
