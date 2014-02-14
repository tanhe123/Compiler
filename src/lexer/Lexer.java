package lexer;

import java.io.IOException;
import java.util.Hashtable;

public class Lexer {
	public int line = 1;
	private char peek = ' ';
	private Hashtable<String, Word> words = new Hashtable<>();
	void reserve(Word t) { words.put(t.lexeme, t); }
	public Lexer() {
		reserve(Word.True); reserve(Word.False);
	}
	
	void readch() throws IOException { peek = (char)System.in.read(); }
	boolean readch(char c) throws IOException {
		readch();
		if (peek != c) return false;
		peek = ' ';
		return true;
	}
	
	public Token scan() throws IOException {
		for ( ; ; readch()) {
			if (peek == ' ' || peek == '\t') continue;
			else if (peek == '\n') line++;
			else break;
		}
		
		switch (peek) {
		case '&':
			if (readch('&')) return Word.and; else return new Token('&');
		case '|':
			if (readch('|')) return Word.or;  else return new Token('|');
		case '=':
			if (readch('=')) return Word.eq;  else return new Token('=');
		case '!':
			if (readch('=')) return Word.ne;  else return new Token('=');
		case '<':
			if (readch('=')) return Word.le;  else return new Token('<');
		case '>':
			if (readch('=')) return Word.ge;  else return new Token('>');	
		}
		
		// 数字
		if (Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10*v + Character.digit(peek, 10);
				readch();
			} while (Character.isDigit(peek));
			
			if (peek != '.') return new Num(v);
			
			float x = v; float d = 10;
			for (;;) {
				readch();
				if (!Character.isDigit(peek)) break;
				x = x + Character.digit(peek, 10) / d; d = d * 10;
			}
			
			return new Real(x);
		}
		
		// 关键字或标识符
		if (Character.isLetter(peek)) {
			StringBuilder b = new StringBuilder();
			do {
				b.append(peek);
				readch();
			} while (Character.isLetterOrDigit(peek));
			
			String s = b.toString();
			Word w = words.get(s);
			if (w != null) return w;
			else {
				w = new Word(s, Tag.ID);
				words.put(s, w);
				return w;
			}
		}
		
		Token t = new Token(peek);
		peek = ' ';
		return t;
	}
}
