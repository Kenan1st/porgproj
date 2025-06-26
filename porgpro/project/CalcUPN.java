package project;

import project.tok.Model.*;
import project.tok.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;

public class CalcUPN{
	
	Stack<Num> nums = new Stack<>();
	Token [] tok;
	double sol = 0.0;

	public CalcUPN(Token[] tk){

		this.tok = tk;
		
		for(int i = 0; i< tk.length; i++){
			if(this.tok[i] instanceof Num){
				this.nums.push((Num)this.tok[i]);
				continue;
			}
			else {
				if(this.tok[i] instanceof PI){
					this.nums.push(new Num(Math.PI));
					continue;
				}
				if(this.tok[i] instanceof EUL){
					this.nums.push(new Num(Math.E));
					continue;
				}
				else{
					double result = calc(this.tok[i]);
    					this.nums.push(new Num(result));
				}
			}
		}	
		this.sol = this.nums.pop().value();
	}

	public double calc(Token t){
		return switch(t){
			
			case Op.ADD-> 
				{double right = this.nums.pop().value();
    				double left = this.nums.pop().value();
    				yield left + right;}
			
			case Op.SUB-> 
				{double right = this.nums.pop().value();
    				double left = this.nums.pop().value();
    				yield left - right;}
			
			case Op.DIV->
				{double right = this.nums.pop().value();
    				double left = this.nums.pop().value();
    				yield left / right;}
			
			case Op.MUL->
				{double right = this.nums.pop().value();
    				double left = this.nums.pop().value();
    				yield left * right;}
			
			case Op.POW-> {
				double right = this.nums.pop().value();
				double left = this.nums.pop().value();
				yield Math.pow(left,right) ;}
			
			default -> this.func(t);
		};
	}

	public double func(Token t){
		if(t instanceof SIN){
			double cont = this.nums.pop().value();
			return Math.sin(cont);
			}
		if(t instanceof TAN){
			double cont = this.nums.pop().value();
			return Math.tan(cont);
			}
		if(t instanceof COS){
			double cont = this.nums.pop().value();
			return Math.cos(cont);
			}
		if(t instanceof LOG){
			double right = this.nums.pop().value();
			double left = this.nums.pop().value();
			return Math.log(left) / Math.log(right);
			}
		if(t instanceof SQRT){
			double cont = this.nums.pop().value();
			return Math.sqrt(cont);
			}
		else{
			throw new IllegalArgumentException("Token Unbekannt" + t);
		}
	}
}
