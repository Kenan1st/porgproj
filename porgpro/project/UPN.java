package project;

import project.tok.Tokenizer;
import project.tok.Model.*;
import java.util.ArrayList;

public class UPN{

	Tokenizer T;
	Token[] tok;
	ArrayList <Token> prio = new ArrayList<>();

	public UPN(String s){

		this.T = new Tokenizer(s);
		this.tok = this.T.maker();

		for(int i = 0; i<this.tok.length; i++){
			
			if(this.tok[i] instanceof Num){
				this.prio.add(this.tok[i]);
				continue;
			}
			else{
				this.prio.add(this.tok[i])
			}
		}
	}
}
