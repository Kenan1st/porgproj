package project.ast;

import java.util.List;
import project.CalcTree;

public record Func(Funcs f, List<Expr> e) implements Expr{

	public double function(){
		CalcTree c = new CalcTree(this.e.get(0));
		double cup = c.calc();
		return switch(this.f()){
			case Funcs.SIN -> Math.sin(cup);
			case Funcs.COS -> Math.cos(cup);
			case Funcs.TAN -> Math.tan(cup);
			case Funcs.LOG -> Math.log(cup);
			case Funcs.SQRT -> Math.sqrt(cup);
			default -> throw new IllegalArgumentException("keine bekannte Funktion");
		};
	}
}
