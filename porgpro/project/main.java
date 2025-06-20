package project;

import project.tok.Model.*;
import project.tok.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class main{

	public static void main(String[]args){

		Tokenizer TO = new Tokenizer("4+2^sin(2*5*7/8-(-1))");
		Token [] t = TO.maker();
		FunctionResolver fr = new FunctionResolver();
		var Z = fr.resolveAll(t);
		UPN UP = new UPN();
		var U = UP.UPN(Z);
		System.out.println(U);
	}
}
