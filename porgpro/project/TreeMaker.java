package project;

import project.ast.*;
import project.tok.Model.*;
import project.tok.*;

public class TreeMaker{

	public int k;

	public TreeMaker(){
		this.k = 0;
	}

	public String planter(Expr e_in){
		
		String connects = "";

		if(e_in instanceof BOp){
			
			connects += this.planter(((BOp)e_in).e_1()) + "\n";
			int left = this.k;
			connects += this.planter(((BOp)e_in).e_2()) + "\n";
			int right = this.k;
			this.k += 1;

			connects += "n"+this.k+ "[label=\""+((BOp)e_in).a()+"\"] ;\n";
			connects += "n"+this.k+" -> " + "n"+left+";\n";
			connects += "n"+this.k+" -> " + "n"+right+";\n";

		}
		if(e_in instanceof Func){
			connects += this.planter(((Func)e_in).e().get(0))+"";

			int down = this.k;
			this.k += 1;

			connects += "n"+this.k+"[label=\""+((Func)e_in).f() + "\"] ;\n";

			connects += "n"+this.k+ " -> " +"n"+ down + ";\n";

			if(((Func)e_in).f() == Funcs.LOG){
				int root = this.k;
				connects += this.planter(((Func)e_in).e().get(1))+"\n";
				connects += "n"+root+ " -> n" +this.k+ " ;\n";
			}
		}
		if(e_in instanceof Va){
			this.k += 1;
			connects += "n"+this.k+"[label=\""+((Va)e_in).name()+"\"];";
		}

		if(e_in instanceof Cnst){
			this.k += 1;
			connects += "n"+this.k+"[label=\""+((Cnst)e_in).cnst()+"\"];";
		}
		return connects;
	}
}

