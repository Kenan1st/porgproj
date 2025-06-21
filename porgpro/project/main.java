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

		Tokenizer TO = new Tokenizer("8+21+123*312");
		Token [] t = TO.maker();
		
		System.out.println(Arrays.toString(t));
		FunctionResolver fr = new FunctionResolver();
		
		var Z = fr.resolveAll(t);
		UPN UP = new UPN();
		var U = UP.UPN(Z);
		System.out.println(U);
		Token[] TKK = U.toArray(new Token[0]);
		fr.resolveAll(TKK);
		System.out.println(fr.getExpr());
	}
}
