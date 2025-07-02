package project;

import project.ast.*;
import project.tok.Model.*;
import project.tok.*;

public class Plotter{

	public int k;

	public Plotter(){
		this.k = 0;
	}

	public String plot(Expr e_in){
		
		String connects = "";

		if(e_in instanceof BOp){
			
			connects += this.plot(((BOp)e_in).e_1()) + "\n";
			int left = this.k;
			connects += this.plot(((BOp)e_in).e_2()) + "\n";
			int right = this.k;
			this.k += 1;

			connects += "n"+this.k+ "[label=\""+((BOp)e_in).a()+"\"] \n";
			connects += "n"+this.k+" -> " + "n"+left+"\n";
			connects += "n"+this.k+" -> " + "n"+right+"\n";

		}
		if(e_in instanceof Func){
			this.k += 1;
			connects += "n"+this.k+"[label=\""+((Func)e_in).f() + "\"] \n";
			//this.k+=1;
			connects += this.plot(((Func)e_in).e().get(0))+"";

			int down = this.k;
			connects += "n"+this.k+ " -> " +"n"+down + "\n";
		}
		if(e_in instanceof Va){
			this.k += 1;
			connects += "n"+(this.k)+"[label=\""+((Va)e_in).name()+"\"]";
		}

		if(e_in instanceof Cnst){
			this.k += 1;
			connects += "n"+this.k+"[label=\""+((Cnst)e_in).cnst()+"\"]";
		}
		return connects;
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
