void main(){

 Clerk.clear();

    String exampleValue = "0.2*x^2 - 0.7*x^3 + 5*x"; // Input Example
	//
   	Clerk.markdown(Text.fillOut("""
		Momentane arithmetische Ausdruck ist:  """+
		exampleValue + """
		geben sie hier ihren eigenen ein:
		"""));
    Clerk.write(Interaction.input("./Combined.java", "// Input String Example", "String exampleValue = \"$\";", "Geben Sie einen UPN ausdruck ein"));

    Clerk.markdown(Text.fillOut(""" 
		## Hier sehen sie den SyntaxBaum zum arithmetischen Ausdruck
		""", Text.codeBlock("./Plotter.java" , "//Input")));

	Tokenizer TO = new Tokenizer(exampleValue);
	
	Token [] t = TO.maker();

	FunctionResolver fr = new FunctionResolver();
	
	Token [] Z = fr.resolveAll(t);

	Validater val = new Validater(Z);

	String dot_arg = "";

	Dot dot = new Dot();

	if(val.inf){
		Inf2Upn i2U = new Inf2Upn(Z);
		Z = i2U.outPutToken;
	}

	Plotter PO = new Plotter(Z);

	UPNParser P = new UPNParser(Z);

	Expr g = P.parse();

	CalcTree U = new CalcTree(g);

	TreeMaker TM = new TreeMaker();

	dot_arg = TM.planter(g);	
	
	if(val.ar_expr){

	CalcUPN solution = new CalcUPN(Z);

	Clerk.markdown(Text.fillOut("""
		das Egebnis des arithmethischen ausdrucks ist : """+
		solution.sol +"""
	"""));
	}
		dot.draw("digraph G {"+dot_arg+"}");

		PO.t.write();

}
