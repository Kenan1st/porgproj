package project;

import project.tok.Tokenizer;
import project.tok.Model.*;
import java.util.ArrayList;

import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class UPN{
	
	public Queue<Token> UPN(Token[] tokens){

		Stack<Token> operatorStack = new Stack<>();
		Queue<Token> outputQueue = new LinkedList<>();

		for(Token tk:tokens){

			if(tk instanceof Num || tk instanceof Ident){
				outputQueue.add(tk);
			}
			if(tk instanceof SIN || tk instanceof COS || tk instanceof TAN || tk instanceof SQRT || tk instanceof LOG){
				operatorStack.add(tk);
			}
			if(tk instanceof Op){
				while(!operatorStack.isEmpty() && 
					(operatorStack.peek() instanceof SIN ||
					 operatorStack.peek() instanceof COS ||
					 operatorStack.peek() instanceof TAN ||
					 operatorStack.peek() instanceof SQRT||
					 operatorStack.peek() instanceof LOG ||
					 operatorStack.peek() instanceof Op)
						&& this.compare(operatorStack.peek(),tk)){
				
					outputQueue.add(operatorStack.pop());
				}
				operatorStack.add(tk);
			}
			if(tk == Sp.OPEN){
				operatorStack.add(tk);
			}
			if(tk == Sp.CLOSED){
				while(!operatorStack.isEmpty() && operatorStack.peek()!= Sp.OPEN){
					outputQueue.add(operatorStack.pop());
				}
				if(operatorStack.isEmpty()){
					throw new IllegalArgumentException("Fehlende Klammer");
				}
				operatorStack.pop();
				if(!operatorStack.isEmpty() && 
					(operatorStack.peek() instanceof SIN ||
					 operatorStack.peek() instanceof COS ||
					 operatorStack.peek() instanceof TAN ||
					 operatorStack.peek() instanceof SQRT||
					 operatorStack.peek() instanceof LOG )){
					outputQueue.add(operatorStack.pop());
				}
			}
		}

		while(!operatorStack.isEmpty()){
			if(operatorStack.peek() instanceof Sp){
				throw new IllegalArgumentException("Fehlende Klammer");
			}
			outputQueue.add(operatorStack.pop());
		}

		return outputQueue;
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
			case Op.NEG -> 40;
			case Sp.OPEN -> 50;
			case Sp.CLOSED -> 50;
			case Num n -> throw new IllegalArgumentException();
			default -> 45;
		};
	}
}
