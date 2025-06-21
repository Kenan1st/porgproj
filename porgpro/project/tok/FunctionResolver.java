package project.tok;

import project.tok.Model.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionResolver {

	static String Expr= "";
	
    // Eine Liste der bekannten Funktionen.
    // Hier könntest du die Funktionsnamen als Schlüssel definieren und entsprechende Token-Klassen zuweisen.
	public static Token resolve(Token token) {
		if (token instanceof Ident identToken) {
		String name = identToken.name().toLowerCase();
		return switch (name) {
			case "sin" -> new SIN();
			case "cos" -> new COS();
			case "tan" -> new TAN();
			case "sqrt" -> new SQRT();
			case "log" -> new LOG();
			default -> token; // Kein Funktionsname – bleibt ein generischer Ident-Token.
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
			
				s += switch(R){
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
						{if(R instanceof SIN){yield "sin ";}
						if(R instanceof COS){yield "cos ";}
						if(R instanceof TAN){yield "tan ";}
						if(R instanceof SQRT){yield "sqrt ";}
						if(R instanceof LOG){yield "log ";}
						if(R instanceof PI){yield "π ";}
						if(R instanceof EUL){yield "e ";}
						else{throw new IllegalArgumentException("Nicht erkannter Token");}}
			};
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
}

