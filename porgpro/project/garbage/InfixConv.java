package project;

import project.tok.Model.*;
import project.tok.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.ArrayDeque;

public class InfixConv{
	
	public String InfixConv(Token [] tok){

	Stack<String> s = new Stack<>();

	for(Token tk : tok){
		if(tk instanceof Num || tk instanceof Pi || tk instanceof Eul){
			s.push(tk.toString());
			continue;
		}

		if(tk instanceof Sin || tk instanceof Cos || tk instanceof Tan || tk instanceof Sqrt || tk instanceof Log){
			String operand = s.pop();

			String Ausdruck = tk.toString() + "(" + operand + ")";
			s.push(Ausdruck);
			continue;
		}
		if(tk instanceof Op){
			String rightop = s.pop();
			String leftop = s.pop();

			String Ausdruck = "(" + leftop + " " + tk.toString() + " " + rightop + ")";
			s.push(Ausdruck);
			continue;
		}
		else{
			throw new IllegalArgumentException("Unerkannter Token");
		}
	}
	
	if(s.size() != 1){throw new IllegalArgumentException("Falscher RPN-AUSDRUCK");}
	
	return s.pop();
	}
}
