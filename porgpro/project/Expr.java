/*package project;

import project.tok.*;
import project.tok.Model.*;
import java.util.ArrayList;

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
									Arrays.copyOfRange(this.T,i+1,brFinder(i+1))));
						case "cos" -> new COS(
								new Expr(
									Arrays.copyOfRange(this.T,i+1,brFinder(i+1))));
						case "tan" -> new TAN(
								new Expr(
									Arrays.copyOfRange(this.T,i+1,brFinder(i+1))));
						case "sqrt"-> new SQRT(
								new Expr(
									Arrays.copyOfRange(this.T,i+1,brFinder(i+1))));
						case "log" -> new LOG(
								new Expr(
									Arrays.copyOfRange(this.T,i+1,brFinder(i+1))));
						};
				continue;
			}
			
			if(k instanceof Sp){

				if(k == Sp.OPEN){
					int u = this.brFinder(i-1);
					new Expr(Arrays.copyOfRange(this.T,i,u));
					i = u;
					continue;
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
				cnt--;
			}
			if(cnt == 0){
				place = i+1;
				return;
			}
		}
		
		if(cnt != 0){throw new IllegalArgumentException("Falsche Klammern gesetzt");}

		return place;
	}
}*/
