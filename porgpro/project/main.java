void main(){

 Clerk.clear();

    String exampleValue; // Input Example
    Clerk.write(Interaction.input("./Combined.java", "// Input String Example", "String exampleString = \"$\";", "Geben Sie einen UPN ausdruck ein"));

    Clerk.markdown(Text.fillOut(""" 
		## Hier sehen sie den SyntaxBaum zum arithmetischen Ausdruck
		""", Text.codeBlock("./Plotter.java" , "//Input")));

	Tokenizer TO = new Tokenizer(exampleValue);
	
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



    // Dot
    Dot dot = new Dot();
    dot.draw("digraph G {"+h+"}");
    // Dot
	//
	PO.t.write();
}
