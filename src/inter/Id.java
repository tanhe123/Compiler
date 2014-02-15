package inter;

import lexer.*;
import symbols.*;

/**
 * 因为一个标识符就是一个地址，类ID从类Expr中记成了gen和reduce的默认实现
 * @author tan
 *
 */
public class Id extends Expr{
	public int offset;
	public Id(Word id, Type p, int b) { super(id, p); offset = b; }
}
