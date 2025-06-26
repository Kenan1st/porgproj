package project.ast;

import java.util.List;

public record Func(Funcs f, List<Expr> e) implements Expr{

	public double function(){
		return switch(this.f()){
			case Funcs.SIN -> Math.sin(((Cnst)this.e.get(0)).cnst());
			case Funcs.COS -> Math.cos(((Cnst)this.e.get(0)).cnst());
			case Funcs.TAN -> Math.tan(((Cnst)this.e.get(0)).cnst());
			case Funcs.LOG -> Math.log(((Cnst)this.e.get(0)).cnst());
			case Funcs.SQRT -> Math.sqrt(((Cnst)this.e.get(0)).cnst());
			default -> throw new IllegalArgumentException("keine bekannte Funktion");
		};
	}
}
