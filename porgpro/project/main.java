package project;

import project.tok.Model.*;
import project.tok.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

void main(){

		Tokenizer TO = new Tokenizer("e 2 ^ 5 * sin tan");
		
		Token [] t = TO.maker();

		FunctionResolver fr = new FunctionResolver();
		
		Token [] Z = fr.resolveAll(t);

		Plotter PO = new Plotter();

		PO.drawFunc(Z);

		UPNParser P = new UPNParser(Z);

		Expr g = P.parse();

		CalcTree U = new CalcTree(g);

		TreeMaker TM = new TreeMaker();

		String h = TM.planter(g);

}
