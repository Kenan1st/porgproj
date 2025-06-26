package project.ast;

public record BOp(BinOp a, Expr e_1 ,Expr e_2) implements Expr{

	public BOp(){
		return switch(this.a()){
			case BinOp.ADD -> this.ExpConv(this.e_1()) + this.ExpConv(this.e_2());
			case BinOp.SUB -> this.ExpConv(this.e_1()) - this.ExpConv(this.e_2());
			case BinOp.MUL -> this.ExpConv(this.e_1()) * this.ExpConv(this.e_2());
			case BinOp.DIV -> this.ExpConv(this.e_1()) / this.ExpConv(this.e_2());
			case BinOp.POW -> this.ExpConv(this.e_1()) ^ this.ExpConv(this.e_2());
		};
	}

	public double expConv(Expr e){
		if(!(e instanceof Cnst)){
			if(e instanceof Func){
				return ((Func)e).value();
			}
			if(e instanceof Va){
				
			}
			if(e instanceof UOp){
				return ((UOp)e).value();
			}
			if(e instanceof BOp){
				return ((BOp)e).Bop();
			}
		}
		else{
			return ((Cnst)e).cnst();
		}
	}

}
