package inter;

import symbols.Type;
import lexer.Token;

public class Expr extends Node{
	public Token op;		// 结点运算符
	public Type type;		// 结点的类型
	
	public Expr(Token tok, Type p) { op = tok; type = p; }
	
	/**
	 * gen 返回了一个 “项”， 该项可以成为一个三地址指令的右部。
	 * 给定一个表达式 E = E1+E2, 方法gen返回一个项 x1+x2, 
	 * 其中x1和x2分别存放E1和E2的地址。如果这个对象是一个地址，就可以返回this值。
	 * Expr的子类通常会重新实现 gen
	 */
	public Expr gen() { return this; }
	
	/**
	 * 把一个表达式计算(或者说"归约")成为一个单一的地址。
	 * 也就是说他返回一个常量、一个标识符，或者一个临时名字。
	 * 给定一个表达式E， 方法reduce返回一个存放E的值的临时变量t
	 * 如果这个对象是一个地址，那么this仍然是正确的返回值
	 * @return
	 */
	public Expr reduce() { return this; }
	
	public void jumping(int t, int f) {	}
	public void emitjumps(String test, int t, int f) {
		
	}
}
