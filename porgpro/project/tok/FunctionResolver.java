package project.tok;

import project.tok.Model.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionResolver {

	static String Expr= "";
	
	public static Token resolve(Token token) {
		if (token instanceof Ident identToken) {
		String name = identToken.name().toLowerCase();
		return switch (name) {
			case "sin" -> new SIN();
			case "cos" -> new COS();
			case "tan" -> new TAN();
			case "sqrt" -> new SQRT();
			case "log" -> new LOG();
			default -> token;
			};
		}
		return token;
	}

	public static Token[] resolveAll(Token[] tokens) {
		List<Token> result = new ArrayList<>();
		String s = "";
		for (Token token : tokens) {
			var R = resolve(token);
			result.add(R);
			s += stringify(R);
						
		}
	putin(s);
	Expr = s;
	return result.toArray(new Token[0]);
    }

	public String getExpr(){
		return this.Expr;
	}

	public static void putin(String s){
		Expr = s;
	}

	public static String stringify(Token t){
		return switch(t){
					case Num n -> String.valueOf(n.value())+ " ";
					case Op.ADD -> "+ ";
					case Op.SUB -> "- ";
					case Op.MUL -> "* ";
					case Op.DIV -> "/ ";
					case Op.POW -> "^ ";
					case Sp.OPEN -> "( ";
					case Sp.CLOSED -> ") ";
					case Sp.KOMMA -> ", ";
					default -> 
						{if(t instanceof SIN){yield "sin ";}
						if(t instanceof COS){yield "cos ";}
						if(t instanceof TAN){yield "tan ";}
						if(t instanceof SQRT){yield "sqrt ";}
						if(t instanceof LOG){yield "log ";}
						if(t instanceof PI){yield "π ";}
						if(t instanceof EUL){yield "e ";}
						else{throw new IllegalArgumentException("Nicht erkannter Token");}}
			};

	}
}

