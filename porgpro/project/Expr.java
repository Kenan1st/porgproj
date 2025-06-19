package project;

import project.tok.*;
import project.tok.Model.*;

public class Expr{

	Token[] T;

	public Expr(Token[] t){
		
		this.T = t;
		for(int i = 0; i<this.T.length;i++){
		
			var k = this.T[i];
			if(k instanceof Ident){

				this.T[i] = switch(k.name()){
						case "sin" -> new SIN(
								new Expr(
									Array.copyOfRange(this.T,i+1,brFinder(i+1))));
						case "cos" -> new COS(
								new Expr(
									Array.copyOfRange(this.T,i+1,brFinder(i+1))));
						case "tan" -> new TAN(
								new Expr(
									Array.copyOfRange(this.T,i+1,brFinder(i+1))));
						case "sqrt"-> new SQRT(
								new Expr(
									Array.copyOfRange(this.T,i+1,brFinder(i+1))));
						case "LOG" -> new LOG(
								new Expr(
									Array.copyOfRange(this.T,i+1,brFinder(i+1))));
						};
			}
			
			if(k instanceof Sp){
				if(k == Sp.OPEN){
					int u = this.brFinder(i-1);
					new Expr(Arrays.copyOfRange(this.T,i,j));
					i = u;
				}
				else{
					throw new IllegalArgumentException("Klammern falsch gesetzt");
				}
			}

		}
	}

	public int brFinder(int j){
		
		int place = 0;
		int cnt = 0;
		
		for(int i = j; i<this.T.length; i++){
			if(this.T[i] == Sp.CLOSED){
				cnt++;
			};
			if(this.T[i] == Sp.OPEN){
				cnt++;
			}
			if(cnt == 1){
				place = i+1;
				return;
			}
		}
		return place;
	}
}
