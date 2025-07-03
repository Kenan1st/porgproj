void main(){

		Tokenizer TO = new Tokenizer("x 2 ^ x +");
		
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

 Clerk.clear();

    // Dot
    Dot dot = new Dot();
    dot.draw("digraph G {"+h+"}");
    // Dot
	//
	PO.t.write();
}
