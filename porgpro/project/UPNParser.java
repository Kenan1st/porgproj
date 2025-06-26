package project;

import project.tok.Model.*;
import project.tok.*;
import project.ast.*;
import project.ast.Expr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UPNParser{
	
	private ArrayList<Token> t;

	public UPNParser(Token [] tok){

		this.t = new ArrayList<>(Arrays.asList(tok));
		
	}

	public Expr parse(){
		
		var z = this.t.removeLast();

		return switch(z){
			
				case Num n -> new Cnst(n.value());
				case Ident s -> new Va(s.name());
				case Op o -> {	var p = this.OpConv(o);
						var r = this.parse();
						var l = this.parse();
						yield new BOp(p,l,r);
						}
				case Sin() -> new Func(Funcs.SIN, List.of(this.parse()));
				case Cos() -> new Func(Funcs.COS, List.of(this.parse()));
				case Tan() -> new Func(Funcs.TAN, List.of(this.parse()));
				case Log() -> new Func(Funcs.LOG, List.of(this.parse()));
				case Sqrt() -> new Func(Funcs.SQRT, List.of(this.parse()));
				case Pi() -> new Cnst(Math.PI);
				case Eul() -> new Cnst(Math.E);
				default -> throw new IllegalArgumentException("Fehler in Token");

			};
	}

	public BinOp OpConv(Op o){
		return switch(o){
			case Op.ADD -> BinOp.ADD;
			case Op.SUB -> BinOp.SUB;
			case Op.MUL -> BinOp.MUL;
			case Op.DIV -> BinOp.DIV;
			case Op.POW -> BinOp.POW;
			default -> throw new IllegalArgumentException("wie auch immer das immermöglich");
		};
	}


}
