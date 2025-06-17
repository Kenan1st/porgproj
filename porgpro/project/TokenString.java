package project;

import project.tok.Tokenizer;
import project.tok.Model.*;
import java.util.ArrayList;

public class TokenString{

	Tokenizer T;
	Token[] tok;
	ArrayList<Integer> prio = new ArrayList<>();
	
	public TokenString(String s){
		this.T = new Tokenizer(s);
		this.tok = this.T.maker();

		for(int i = 0; i < tok.length; i++){
			prio.add(prioConv(this.tok[i]));
		}
	}

	public int prioConv(Token t){
		if(t instanceof Num){
			return 0;
		}
		else{
			return chsOp(t);
		}
	}

	public int chsOp(Token t){

		if(t instanceof Op)
			return switch(t){
				case Op.ADD -> 60;
				case Op.SUB -> 60;
				case Op.MUL -> 70;
				case Op.DIV -> 70;
				case Op.POW -> 90;
				case Op.NEG -> 80;
				default -> throw new IllegalArgumentException();
		};

		if(t instanceof Sp){
			return 100;
		}
		if(t instanceof TF){
			return 95;
		}
		else{
			throw new IllegalArgumentException();
		}
	}
}
