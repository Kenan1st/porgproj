package project.tok;

import project.tok.Model.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionResolver {

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
        for (Token token : tokens) {
            result.add(resolve(token));
        }
        return result.toArray(new Token[0]);
    }
}

