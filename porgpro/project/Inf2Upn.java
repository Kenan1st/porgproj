package project;

import project.tok.Model.*;
import project.tok.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.ArrayDeque;


public class Inf2Upn{

	Stack<Token>operatorStack;

	Token[] outPutToken;
	
	public Inf2Upn(Token[] tokens){

		this.operatorStack = new Stack<>();
		ArrayList<Token> outputQueue = new ArrayList<>();

		for(int i = 0; i<tokens.length;i++){

			Token tk = tokens[i];

			if(tk instanceof Num || tk instanceof Ident || tk instanceof Eul || tk instanceof Pi){
				outputQueue.add(tk);
			}
			if(tk instanceof Sin || tk instanceof Cos || tk instanceof Tan || tk instanceof Sqrt || tk instanceof Log || tk instanceof Ln){
				this.operatorStack.add(tk);
			}
			if(tk instanceof Op){
				while(!this.operatorStack.isEmpty() && 
					(this.operatorStack.peek() instanceof Sin ||
					 this.operatorStack.peek() instanceof Cos ||
					 this.operatorStack.peek() instanceof Tan ||
					 this.operatorStack.peek() instanceof Sqrt||
					 this.operatorStack.peek() instanceof Log ||
					 this.operatorStack.peek() instanceof Op)
						&& this.compare(this.operatorStack.peek(),tk)){
				
					outputQueue.add(this.operatorStack.pop());
				}
				this.operatorStack.add(tk);
			}
			if(tk instanceof Space){continue;}
			if(tk == Sp.OPEN){
				this.operatorStack.add(tk);
			}
			if(tk == Sp.CLOSED){
				while(!this.operatorStack.isEmpty() && this.operatorStack.peek()!= Sp.OPEN){
					outputQueue.add(this.operatorStack.pop());
				}
				if(this.operatorStack.isEmpty()){
					throw new IllegalArgumentException("Fehlende Klammer");
				}
				this.operatorStack.pop();
				if(!this.operatorStack.isEmpty() && 
					(this.operatorStack.peek() instanceof Sin ||
					 this.operatorStack.peek() instanceof Cos ||
					 this.operatorStack.peek() instanceof Tan ||
					 this.operatorStack.peek() instanceof Sqrt||
					 this.operatorStack.peek() instanceof Log ||
					 this.operatorStack.peek() instanceof Ln)){
					outputQueue.add(this.operatorStack.pop());
				}
			}
		}

		while(!this.operatorStack.isEmpty()){
			if(this.operatorStack.peek() instanceof Sp){
				throw new IllegalArgumentException("Fehlende Klammer");
			}
			outputQueue.add(this.operatorStack.pop());
		}

		this.outPutToken = new Token[outputQueue.size()];

		for(int i = 0; i<outputQueue.size();i++){
			this.outPutToken[i]  =  outputQueue.get(i);
		}
	}

	public boolean compare(Token StT, Token tk){
		if(prio(StT) >= prio(tk)){
			return true;
		}
		else{
			return false;
		}
		
	}

	public int prio(Token t){
		return switch(t){
			case Op.ADD -> 10;
			case Op.SUB -> 10;
			case Op.MUL -> 20;
			case Op.DIV -> 20;
			case Op.POW -> 30;
			case Sp.OPEN -> 50;
			case Sp.CLOSED -> 50;
			case Num n -> throw new IllegalArgumentException();
			default -> 45;
		};
	}
}


