import java.io.IOException;
import java.util.Hashtable;


class Token {
	public final int tag;
	public Token(int tag) {	this.tag = tag;	}
	public String toString() { return "" + (char)tag; }
}

class Tag {
	public final static int 
		NUM = 256, ID = 257, TRUE = 258, FALSE = 259, REAL = 260,
		EQ = 261, GE = 262, LE = 263, NE = 264, AND = 265, OR = 266;  
}

class Num extends Token {
	public final int value;
	public Num(int v) {	super(Tag.NUM);	value = v; }
	public String toString() { return "" + value; }
}

// 实数类
class Real extends Token {
	public final float value;
	public Real(float v) { super(Tag.REAL);	value = v; }
	public String toString() { return "" + value; }
}

class Word extends Token {
	public final String lexeme;
	public Word(String s, int t) { super(t); lexeme = s; }
	public String toString() { return lexeme; }
	public static final Word
		and	= new Word("&&", Tag.AND), or = new Word("||", Tag.OR),
		eq	= new Word("==", Tag.EQ ), ne = new Word("!=", Tag.NE),
		le  = new Word("<=", Tag.LE ), ge = new Word(">=", Tag.GE);
}

public class Lexer {
	public static void main(String[] args) throws IOException {
		Lexer lexer = new Lexer();
		for (int i=0; i<20; i++) {
			Token token = lexer.scan();

			System.out.println(token.toString());
		}
	}
	
	public int line = 1;
	private char peek = ' ';
	private Hashtable<String, Word> words = new Hashtable<>();
	void reserve(Word t) { words.put(t.lexeme, t); }
	public Lexer() {
		reserve(new Word("true", Tag.TRUE));
		reserve(new Word("false", Tag.TRUE));
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
