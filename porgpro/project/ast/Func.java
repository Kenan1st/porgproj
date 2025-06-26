package project.ast;

import java.util.List;

public record Func(Funcs f, List<Expr> e) implements Expr{

	/*public double Func(){
		return switch(this.f()){
			case f.SIN -> Math.sin();
			case f.COS -> Math.cos();
			case f.TAN -> Math.tan();
			case f.LOG -> Math.log();
			case f.SQRT -> Math.sqrt();
			default -> throw new IllegalArgumentException("keine bekannte Funktion");
		};
	}*/
}
