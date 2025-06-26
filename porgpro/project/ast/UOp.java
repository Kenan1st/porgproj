package project.ast;

public enum UOp implements Expr{
	POS,
	NEG;

	public double apply(double d){
		return switch(this){
			case POS -> d;
			case NEG -> -d;
		};
	}
}
