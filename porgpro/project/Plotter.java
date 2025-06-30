package project;

import project.ast.*;
import project.tok.Model.*;
import project.tok.*;

/*
import lvp.Clerk;
import lvp.skills.Text;
import lvp.skills.Interaction;
import lvp.views.Dot;
import lvp.views.Turtle;
*/
public class Plotter{

	Expr expr;

	public Plotter(Expr e_in){
		this.expr = e_in;
	}

	public String plot(int i){

		String s = "";

		if(this.expr instanceof BOp){
			s = "["+i+"]"+((BOp)this.expr).a() + " -> " + "["+i+"]"+ ((BOp)this.expr).e_1() +";"
					+"\n " + "["+i+"]"+((BOp)this.expr).a() + "["+i+"]"+((BOp)this.expr).e_2() + ";";
		}
		return s;
	}
}
