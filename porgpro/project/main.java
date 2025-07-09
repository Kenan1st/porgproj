void main(){

 Clerk.clear();

    String exampleValue = "x 5 * sin"; // Input Example
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

	Plotter PO = new Plotter(Z);

	Dot dot = new Dot();

	if(val.upn){	

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

	else{
		if(val.inf){
	
		if(val.ar_expr){

		}

		}
		else{
			if(val.ar_expr){
				dot_arg = Z[0].toString();
				PO.t.write();
		}
			else{ throw new IllegalArgumentException("Falscher Ausdruck");}
		}
	}

}

