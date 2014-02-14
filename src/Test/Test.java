package Test;

import java.io.IOException;

import lexer.Lexer;
import lexer.Token;

public class Test {
	public static void main(String[] args) throws IOException {
		Lexer lexer = new Lexer();
		for (int i=0; i<20; i++) {
			Token token = lexer.scan();

			System.out.println(token.toString());
		}
	}
}
