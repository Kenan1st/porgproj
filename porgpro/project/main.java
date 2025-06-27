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

		Tokenizer TO = new Tokenizer("8 4 + 1 + 6 * sin tan");
		Token [] t = TO.maker();

		FunctionResolver fr = new FunctionResolver();
		
		var Z = fr.resolveAll(t);

		UPNParser P = new UPNParser(Z);

		var g = P.parse();

		System.out.println(g);

		CalcTree U = new CalcTree(g);

		System.out.println(U.calc());
		/*System.out.println(Arrays.toString(t));

		UPNConv UP = new UPNConv();
		var U = UP.UPNConv(Z);

		System.out.println(U);

		Token[] TKK = U.toArray(new Token[0]);
		fr.resolveAll(TKK);

		System.out.println(fr.getExpr());
		System.out.println("TKK : "+ Arrays.toString(TKK));

		CalcUPN CP = new CalcUPN(TKK);

		InfixConv IP = new InfixConv();
	
		System.out.println(CP.sol);

		System.out.println(IP.InfixConv(TKK));
		*/
	}

}
