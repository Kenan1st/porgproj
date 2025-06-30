package project;

import project.ast.*;
import project.tok.Model.*;
import project.tok.*;

public class Plotter{

	Expr expr;

	public Plotter(Expr e_in){
		this.expr = e_in;
	}

	public String plot(int i){

		String s = "";

		if(this.expr instanceof BOp){
			s = "["+i+"]"+((BOp)this.expr).a() + " -> " + "["+i+"]"+ ex(((BOp)this.expr).e_1()) +";"
					+"\n" + "["+i+"]"+((BOp)this.expr).a() +" -> "+ "["+i+"]"+ex(((BOp)this.expr).e_2()) + ";" +
					"\n" + new Plotter(((BOp)this.expr).e_1()).plot(i-1) +
					       new Plotter(((BOp)this.expr).e_2()).plot(i+1);
		}
		if(this.expr instanceof Func){
			s += "["+i+"]"+((Func)this.expr).f() + " -> " + "["+i+"]"+ ex(((Func)this.expr).e().get(0)) +";" +
				"\n" + new Plotter(((Func)this.expr).e().get(0)).plot(i+1);
		}
		return s;
	}

	public String ex(Expr e){
		
		return switch(e){
			case BOp b -> b.a()+"";
			case Cnst c -> c.cnst()+"";
			case Va v -> v.name();
			case Func fun -> fun.f()+"";
			default -> throw new IllegalArgumentException("Fehler in ex");
		};

	}
}
