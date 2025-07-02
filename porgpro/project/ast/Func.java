package project.ast;

import java.util.List;
import project.CalcTree;

public record Func(Funcs f, List<Expr> e) implements Expr{

	public double function(){
		CalcTree c = new CalcTree(this.e.get(0));
		
		double cup = c.calc();
		double cup_1;

		if(this.e.size() >= 2){

		CalcTree c_1 = new CalcTree(this.e.get(1)); 
		cup_1 = c_1.calc();
		return trans(cup,cup_1);
		}
		else{
			return trans_1(cup);
		}
		
	}

	public double trans(double d, double d_1){
		return switch(this.f()){
			case Funcs.SIN -> Math.sin(d);
			case Funcs.COS -> Math.cos(d);
			case Funcs.TAN -> Math.tan(d);
			case Funcs.LOG -> Math.log(d) / Math.log(d_1);
			case Funcs.SQRT -> Math.sqrt(d);
			case Funcs.LN -> Math.log(d) / Math.log(Math.E);
			default -> throw new IllegalArgumentException("keine bekannte Funktion");
		};
	}

	public double trans_1(double d){
		return switch(this.f()){
			case Funcs.SIN -> Math.sin(d);
			case Funcs.COS -> Math.cos(d);
			case Funcs.TAN -> Math.tan(d);
			case Funcs.LOG -> Math.log(d);
			case Funcs.SQRT -> Math.sqrt(d);
			case Funcs.LN -> Math.log(d) / Math.log(Math.E);
			default -> throw new IllegalArgumentException("keine bekannte Funktion");
		};
	}
}
