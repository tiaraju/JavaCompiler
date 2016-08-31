package tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java_cup.runtime.Symbol;
import compiler.generated.Parser;
import compiler.generated.Scanner;

public class ParserTest {
	
	public static void main(String[] args) {
		String filePath = "examples/Test.java";
		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(filePath)));
		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		}
		Parser parser = new Parser(scanner);
		Symbol s = null;
		try {
			s = parser.parse();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Done!");
		System.out.println(s);
	}

}
