package project.ast;

import project.tok.Model.*;

public record BOp(BinOp a, Expr e_1 ,Expr e_2) implements Expr{

	public double operation(){
		return switch(this.a()){
			case BinOp.ADD -> this.expConv(this.e_1()) + this.expConv(this.e_2());
			case BinOp.SUB -> this.expConv(this.e_1()) - this.expConv(this.e_2());
			case BinOp.MUL -> this.expConv(this.e_1()) * this.expConv(this.e_2());
			case BinOp.DIV -> this.expConv(this.e_1()) / this.expConv(this.e_2());
			case BinOp.POW -> Math.pow(this.expConv(this.e_1()),this.expConv(this.e_2()));
			default -> throw new IllegalArgumentException("Fehler in BOp");
		};
	}

	public double expConv(Expr e){
		if(!(e instanceof Cnst)){
			if(e instanceof Func){
				return ((Func)e).function();
			}
			if(e instanceof Va){
				throw new IllegalArgumentException("hier kein Identifier");
			}
			if(e instanceof E){
				return Math.E;
			}
			if(e instanceof P){
				return Math.PI;
			}
			if(e instanceof BOp){
				return ((BOp)e).operation();
			}
			else{
				throw new IllegalArgumentException("Fehler in Func");
		}
		}
		else{
			return ((Cnst)e).cnst();
		}
	}

}
