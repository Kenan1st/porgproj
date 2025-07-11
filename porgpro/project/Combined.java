import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import lvp.Clerk;
import lvp.skills.Interaction;
import lvp.skills.Text;
import lvp.views.Dot;
import lvp.views.Turtle;

void main(){

 Clerk.clear();

	Clerk.markdown(Text.fillOut("""
		WICHTIG!!: Bei der Eingabe werden die Zeilen in denen die Werte im Code stehen verschoben und sind somit ab dann nicht mehr Richtig eingerückt.

		Diese Java-Datei ist im folgenden so aufgebaut ,dass sich die Aufgaben des Tokenizer vollständig vom 
		rest trennen.
 		Sprich die enums,records und interfaces mit denen der Tokenizer arbeitet arbeiten nicht bei der 
		Kalkulation der Werte.
		Sie werden umgewandelt, behalten aber ihren Kontext.
		Den Records und enums wurden eigene toString-Methoden geschrieben, damit sie später bei zb. der Dot-Notation eine einfachere
		Serialisierung der Werte ermöglichen. Sie werden auch ähnlich benannt.
		Das einordnen des Ausdrucks in Infix oder UP - Notaton erfolgt über einen 
		Validieren(Validater) 
		```java
		${4}
		```
		der sich
		sehr schlicht die ersten beiden Tokens anschaut und daraufhin validiert,
		ob es sich um die Infix- oder Up-Notation handelt. Fals dem so ist wird der Infix ausdruck in UPN umgewandelt.
		Handelt es sich um einen arithmetischen Ausdruck, welcher keine Funtkion ist (Validater),
		so wird das ergebnis unter dem Baum aufgezeigt und er erscheint als Konstante Linie auf dem Graphen 
		bei dem Lösungswert.
		Die Turtle von dem Mittelpunkt aus nach links und nach rechts aufgebaut und so wir auch die Funktion aufgemalt.
		```java
		${2}
		```
		und auch in den Lines.
		```java
		${3}
		```
		Die Hauptachsen sind hardcoded da sie recht simpel zu kodieren waren
		```java
		${1}
		```
		Dies half bei der Skalireung des Graphen und des Koordinatensystems.
		Je nach Komplexität und Skalierung des Graphen benötigt die website länger zur berechnung, da bei einigen Funktion über 40.000 linien gezeichnet werden
		Momentan ist die die forschleife im Plotter die die menge an Punkten und so auch an linien angibt auf 0.01 gewählt weshalb mann womöglich einige sekunden 
		warten muss um den Graphen angezeigt zu bekommen.

		```java
		${0}
		```
		Zudem wird bei absenden einer Funktion, soweit der Graph noch nicht geladen ist, eine kleine Latenz deutlich, 
		bei der der arithmetische Ausdruck erst nach vollendung des Graphen abgesendet wird.
		```java
		${5}
		```
		""", Text.codeBlock("./Combined.java","// Menge an Punkten -> Linien"),
			Text.codeBlock("./Combined.java","// Hardcode-Turtle"),
			Text.codeBlock("./Combined.java","// left_right_build"),
			Text.codeBlock("./Combined.java","// left_right_lines"),
			Text.codeBlock("./Combined.java","// validater"),
			Text.codeBlock("./Combined.java","// PlotterLines")));

	Clerk.markdown(Text.fillOut("""
		# Funktionen:
		- sqrt(expr)
		- log(basis,expr)
		- trigonometrische Funtkionen zb: sin(expr)
	
		"""));

String exampleValue = "e^x"; // Input String Example

	Clerk.markdown(Text.fillOut("""
		Hier einpaar test funktionen:

		!!! Wichtig: es darf nur eine checkbox angeklickt sein, um die richtige Funktion darzustellen.
		"""));
	
	// ceckbox
boolean test1= true; // test1
	Clerk.markdown(Text.fillOut("""
		sin(e^x)
		"""));
	Clerk.write(Interaction.checkbox("./Combined.java","// test1","boolean test1= $;", test1));
	if(test1){exampleValue = "sin(e^x)";}
	
	// checkbox

	// ceckbox
boolean test2= false; // test2
	Clerk.markdown(Text.fillOut("""
		0.5*x^4 - 2*x^3 + 0.3*x^2 - x
		"""));
	Clerk.write(Interaction.checkbox("./Combined.java","// test2","boolean test2= $;", test2));
	if(test2){exampleValue = "0.5*x^4 - 2*x^3 + 0.3*x^2 - x";}
	
	// checkbox

	// ceckbox
boolean test3= false; // test3
	Clerk.markdown(Text.fillOut("""
		log(x^e,10)
		"""));
	Clerk.write(Interaction.checkbox("./Combined.java","// test3","boolean test3= $;", test3));
	if(test3){exampleValue = "log(x^e,10)";}
	
	// checkbox
	
	// ceckbox
boolean test4= false; // test4
	Clerk.markdown(Text.fillOut("""
		ln(e^x)
		"""));
	Clerk.write(Interaction.checkbox("./Combined.java","// test4","boolean test4= $;", test4));
	if(test4){exampleValue = "ln(e^x)";}
	
	// checkbox
	
	Clerk.markdown(Text.fillOut("""
		Momentane arithmetische Ausdruck ist:  """+
		exampleValue + """
		geben sie hier ihren eigenen ein:
		"""));
	Clerk.write(Interaction.input("./Combined.java", "// Input String Example", "String exampleValue = \"$\";", "Geben Sie einen UPN ausdruck ein"));

int scaleX = 10; // Input scaleX

	Clerk.write(Text.fillOut("""
		Hier können sie die skalierung der x-Achse des gezeigten Graphen angeben:
		"""));
	Clerk.write(Interaction.input("./Combined.java","// Input scaleX", "int scaleX = $;","Geben sie scaleX ein"));

int scaleY = 5; // Input scaleY

	Clerk.write(Text.fillOut("""
		Hier können sie die skalierung der y-Achse des gezeigten Graphen angeben:
		"""));
	Clerk.write(Interaction.input("./Combined.java","// Input scaleY", "int scaleY = $;","Geben sie scaleY ein"));

	Clerk.markdown(Text.fillOut(""" 
		## Hier sehen sie den SyntaxBaum zum arithmetischen Ausdruck
		""", Text.codeBlock("./Combined.java" , "//Input")));

	if(!exampleValue.equals("")){

		Tokenizer TO = new Tokenizer(exampleValue);
	
		Token [] t = TO.maker();

		FunctionResolver fr = new FunctionResolver();	// Der FunctionResolver wandelt identfier sofern es möglich ist in functionen oder die Konstanten pi und e um
							// Er übernimmt das richtige verstehen der eingabe in einen weiterverarbeitenden arithmetischen Ausdruck
		Token [] aexpr_as_token = fr.resolveAll(t);

		Validater val = new Validater(aexpr_as_token);

		String dot_arg = "";				// hier wird das "Argument" für den Dot-Baum initialisiert

		Dot dot = new Dot();
	
		String s = "";

		if(val.inf){						// Wenn der Validierer feststellt, dass es sich um die Infix-Notation handelt, wandelt dann
			//
			Inf2Upn i2U = new Inf2Upn(aexpr_as_token);		// die Inf2Upn Instanz mit Ihrem Konstruktor das TokenArray in die richtige UP-Notation um
			aexpr_as_token = i2U.outPutToken;			// und speichert diese in der gleichen Variable
		}

		else{
			Upn2Inf u2I= new Upn2Inf(aexpr_as_token);
			s = u2I.outputInf;
		}
	
		Plotter PO = new Plotter(aexpr_as_token,(double)scaleX,(double)scaleY);			

		UPNParser P = new UPNParser(aexpr_as_token);			// Der UPNParser wandelt das TokenArray um in eine einzige Expression die in sich noch weitere birgt
		//
		Expr g = P.parse();				
	
		TreeMaker TM = new TreeMaker();			
	
		dot_arg = TM.planter(g);			// Die TreeMaker-instanz TM gibt mit ihrer planter()-Methode die vorher geparste Expression 
								// genau diese Expression in einen String um die den Baum als Dot-Notation darstellt
	
		if(val.ar_expr){				// handelt es Sich bei dem arithmetischen Ausdruck nicht um eine Funtkion sondern einen Bloßen Ausdruck
							// so wird dessen Ergebnis ,in der Variable sol ,in der Instanz solution von der Klasse CalcUPN gespeichert
			CalcUPN solution = new CalcUPN(aexpr_as_token);

			Clerk.markdown(Text.fillOut("""
				das Egebnis des arithmethischen ausdrucks ist : """+
				solution.sol +"""
			"""));
		}
	
		if(val.inf){
			for(Token I2Utoken : aexpr_as_token){s += I2Utoken.toString() +" ";}	// falls es sich um einen UPN-Ausdruck handelt gibt diese forschleife der nächsten Markdown-Zeile ein String
								// mit der Infix-Notation des arithmethischen Ausdrucks wiedier
			Clerk.markdown(Text.fillOut("""
				Hier der UPN Ausdruck zum InfixAusdruck: """+
				s + """
				"""));
		}
		else{
								// mit der Infix-Notation des arithmethischen Ausdrucks wiedier
			Clerk.markdown(Text.fillOut("""
				Hier der Infix Ausdruck zum UPNAusdruck: """+
				s + """
				"""));
		}
			dot.draw("digraph G {"+dot_arg+"}");
	
			PO.t.write();
			PO.t.timelineSlider();
	}
}

class Upn2Inf{

	String outputInf;
	
	public Upn2Inf(Token [] tok){

	Stack<String> s = new Stack<>();

	for(Token tk : tok){
		if(tk instanceof Num || tk instanceof Pi || tk instanceof Eul || tk instanceof Ident){
			s.push(tk.toString());
			continue;
		}

		if(tk instanceof Sin || tk instanceof Cos || tk instanceof Tan || tk instanceof Sqrt || tk instanceof Log || tk instanceof Ln){
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
	
	this.outputInf = s.pop();
	}
}

class Validater{

	boolean upn = false;
	boolean inf = false;
	boolean ar_expr = true;
	
	// validater
	
	public Validater(Token[] tokens){

		if(tokens.length < 2 && tokens[0] instanceof Ident){this.ar_expr = false;}

		if(tokens.length < 2 && tokens[0] instanceof Num){this.ar_expr = true;}

		else{
			if((tokens[0] instanceof Num || tokens[0] instanceof Ident || tokens[0] instanceof Eul || tokens[0] instanceof Pi) &&
			   (tokens[1] instanceof Num || tokens[1] instanceof Ident || tokens[1] instanceof Eul || tokens[1] instanceof Pi)){
				this.upn = true;

				for(Token t : tokens){
					if(t instanceof Ident){this.ar_expr = false;}
				}
			}
			else{
				this.inf = true;
				
				for(Token t : tokens){
					if(t instanceof Ident){this.ar_expr = false;}
			}
			}
		}
		return;
	}
	// validater
}

class Lines{							// Lines bildet die Linien zwischen zwei berechnet Punkten in der Turtle
	Turtle newTurtle;
	double x_s_left;
	double y_s_left;
	double x_s_right;
	double y_s_right;
	ArrayList<Coord> lstCoords_right;
	ArrayList<Coord> lstCoords_left;
	double [] lengths_right;
	double [] lengths_left;
	
	public Lines(Turtle turtle, ArrayList<Coord> oldlstCoords_right, ArrayList<Coord> oldlstCoords_left){ // Im Konstruktor werden 2 Listen und einee Turtle entgegengenommen
									// welche dann als Klassenvariablen verwendet werden und schließlich mit drawLines() das Zeichnen
		// 							// der Linien ausführt
		this.lstCoords_left = oldlstCoords_left;
		this.lstCoords_right = oldlstCoords_right;
		this.newTurtle = turtle;
		
		this.x_s_left = this.lstCoords_left.get(0).x();
		this.y_s_left = this.lstCoords_left.get(0).y();
		this.y_s_right = this.lstCoords_right.get(0).y();
		this.x_s_right = this.lstCoords_right.get(0).x();

		this.drawLines();
		}
	// left_right_lines
	
	public void drawLines(){
	
		for(int i = 1; i<this.lstCoords_left.size();i++){
				Coord nextCoord_right = this.lstCoords_left.get(i);	// in dieser for-schleife geht der code 
				Coord nextCoord_left = this.lstCoords_right.get(i);	// die einzelnen Punkte ab

			// calcLengths berechnet die Hypotenuse und die gradzahl in dem es die Werte x0,x1 und y0,y1 
			// entgegennimmt und diese dann mit dem Satz des Pythagoras und dem arctan ausrechnet
	
				this.lengths_left = 
					calcLengths(this.x_s_left, nextCoord_left.x(), this.y_s_left, nextCoord_left.y());
				this.lengths_right = 
					calcLengths(this.x_s_right, nextCoord_right.x(), this.y_s_right, nextCoord_right.y());

				if(!Double.isFinite(lengths_right[0])){continue;} // Probelembehandlung bei irrellen Zahlen 
										  // wie sqrt(-1)
				if(!Double.isFinite(lengths_right[1])){continue;}

				this.newTurtle.penUp()
						.push()
						.penUp()
						.forward(this.x_s_left) // Zeichnet den linken Teil der Funktion als Graphen
						.left(90)
						.forward(this.y_s_left)
						.right(270-this.lengths_left[1])
						.penDown()
						.forward(this.lengths_left[0])
						.pop()
						.penUp();
				
				this.newTurtle.penUp()
						.push()
						.penUp()
						.forward(this.x_s_right) // Zeichnet den rechten teil der Funktion als Graphen
						.left(90)
						.forward(this.y_s_right)
						.left(90+this.lengths_right[1])
						.penDown()
						.forward(this.lengths_right[0])
						.pop()
						.penUp();
						
				this.x_s_left= nextCoord_left.x(); // die Koordinate zum nächsten Punkt werden als 
								   // Start Punkt der nächsten Linie verwendet
				this.y_s_left = nextCoord_left.y();
				this.y_s_right = nextCoord_right.y();
				this.x_s_right = nextCoord_right.x();}
	}
	// left_right_lines
	public double[] calcLengths(double x_0,double x_1, double y_0,double y_1){
			
			double delt_x = (x_0-x_1);
			double delt_y = (y_0-y_1);

			double hyp = Math.sqrt(
					Math.pow(delt_x,2.0)+
					Math.pow(delt_y,2.0)
					);
			double degree = Math.toDegrees(Math.atan2(delt_y,delt_x));

		return new double[]{hyp,degree};
	}
}

record E() implements Expr{}



enum UOp implements Expr{
	POS,
	NEG;
}

record Cnst(double cnst) implements Expr{}

enum BinOp implements Expr{
	ADD,
	SUB,
	MUL,
	DIV,
	POW
}

enum Funcs{
	SIN,
	COS,
	TAN,
	LOG,
	SQRT,
	LN
}

record P() implements Expr{}

record UExpr(UOp op, Expr e) implements Expr {}

record Func(Funcs f, List<Expr> e) implements Expr{

	public double function(){
		CalcTree c = new CalcTree(this.e.get(0));	// wird zur berechnung in Calctree verwendet und "wechselt" sich mit calctree ab um den Wert auszurehcnen
		
		double cup = c.calc();
		double cup_1;

		if(this.e.size() >= 2){

		CalcTree c_1 = new CalcTree(this.e.get(1)); // dieser Fall kommt nur in Log auf da diese Funktion die einzige ist die 2 Argumente einnimmt
		cup_1 = c_1.calc();
		return trans(cup,cup_1);
		}
		else{
			return trans_1(cup);
		}
		
	}

	public double trans(double d, double d_1){				// Hier wird die jeweilige Mathematische Funktion nach dem enum-namen angewendet
		return switch(this.f()){
			case Funcs.SIN -> Math.sin(d);
			case Funcs.COS -> Math.cos(d);
			case Funcs.TAN -> Math.tan(d);
			case Funcs.LOG -> Math.log(d) / Math.log(d_1);
			case Funcs.SQRT -> Math.sqrt(d);
			case Funcs.LN -> Math.log(d) / Math.log(Math.E);
			default -> throw new IllegalArgumentException("keine bekannte Funktion");
		};
	}

	public double trans_1(double d){
		return switch(this.f()){
			case Funcs.SIN -> Math.sin(d);
			case Funcs.COS -> Math.cos(d);
			case Funcs.TAN -> Math.tan(d);
			case Funcs.LOG -> Math.log(d) / Math.log(2);
			case Funcs.SQRT -> Math.sqrt(d);
			case Funcs.LN -> Math.log(d) / Math.log(Math.E);
			default -> throw new IllegalArgumentException("keine bekannte Funktion");
		};
	}
}

sealed interface Expr permits UExpr,BOp,BinOp,Func,Va,Cnst,UOp,E,P{}

record Va(String name) implements Expr{}

record BOp(BinOp a, Expr e_1 ,Expr e_2) implements Expr{

	public double operation(){
		return switch(this.a()){
			case BinOp.ADD -> this.expConv(this.e_1()) + this.expConv(this.e_2());	// teilt den ausdruck auf und bearbeitet ich bei weiteren expr weiter und berechnet ihn am Ende
			case BinOp.SUB -> this.expConv(this.e_1()) - this.expConv(this.e_2());
			case BinOp.MUL -> this.expConv(this.e_1()) * this.expConv(this.e_2());
			case BinOp.DIV -> this.expConv(this.e_1()) / this.expConv(this.e_2());
			case BinOp.POW -> Math.pow(this.expConv(this.e_1()),this.expConv(this.e_2()));
			default -> throw new IllegalArgumentException("Fehler in BOp");
		};
	}

	// gibt die nächste Expression an

	public double expConv(Expr e){
		if(!(e instanceof Cnst)){
			if(e instanceof Func){
				return ((Func)e).function();
			}
			if(e instanceof Va){
				throw new IllegalArgumentException("hier kein Identifier");
			}
			if(e instanceof E){
				return Math.E;
			}
			if(e instanceof P){
				return Math.PI;
			}
			if(e instanceof BOp){
				return ((BOp)e).operation();
			}
			else{
				throw new IllegalArgumentException("Fehler in expConv: Expression nicht erkannt");
		}
		}
		else{
			return ((Cnst)e).cnst();
		}
	}

}

class TreeMaker{ // baut den Baum auf

	public int k;

	public TreeMaker(){
		this.k = 0;	
	}

	public String planter(Expr e_in){
		
		String connects = "";

		if(e_in instanceof BOp){

			// Jeder Zweig im Baum bekommt eine eigen id und bekommt diese als Label gesetzt
			// Zudem bauen sich die id's von links unten auf was bedeutet dass die kleinste id am tiefsten "linksten" Punkt liegt
			// Vergleichbar wie im Suchbaum in der theoretischen Informatik
			// Zudem durch den rekursiven Aufruf von planter() , wird k immer auch bei der Wurzel erhöht wenn der linke teil gebildet wird
			
			connects += this.planter(((BOp)e_in).e_1()) + "\n";
			int left = this.k;
			connects += this.planter(((BOp)e_in).e_2()) + "\n";
			int right = this.k;
			this.k += 1;

			connects += "n"+this.k+ "[label=\""+((BOp)e_in).a()+"\"] ;\n";
			connects += "n"+this.k+" -> " + "n"+left+";\n";
			connects += "n"+this.k+" -> " + "n"+right+";\n";

		}
		if(e_in instanceof Func){
			connects += this.planter(((Func)e_in).e().get(0))+"";

			int down = this.k;
			this.k += 1;
			int up = this.k;
			connects += "n"+this.k+"[label=\""+((Func)e_in).f() + "\"] ;\n";

			connects += "n"+this.k+ " -> " +"n"+ down + ";\n";

			// Hier wird die ausnahme des Logarithmus in betrahct gezogen bei dem die Liste e() 2 elemte besitzt

			if(((Func)e_in).f() == Funcs.LOG){
				
				int root = this.k;

				connects += this.planter(((Func)e_in).e().get(1))+"\n";
				
				connects += "n"+root+ " -> n" +this.k+ " ;\n";
			}
		}
		// ansonsten werden die id's zu den konstanten oder identifiern gesetzt
		if(e_in instanceof Va){
			this.k += 1;
			connects += "n"+this.k+"[label=\""+((Va)e_in).name()+"\"];";
		}

		if(e_in instanceof Cnst){
			this.k += 1;
			connects += "n"+this.k+"[label=\""+((Cnst)e_in).cnst()+"\"];";
		}
		return connects;
	}
}

class CalcUPN{
	
	Stack<Num> nums = new Stack<>();
	Token [] tok;
	double sol = 0.0;

	public CalcUPN(Token[] tk){



		// hier werden die Werte auf einen Stack gelegt damit man sie bei einer Rechnung
		// pop()-en kann um sie dann zu verwenden und mit dem ergebnis weiter zu rechnen
		// am ende wird das ergebnis in sol gespeichert

		this.tok = tk;
		
		for(int i = 0; i< tk.length; i++){
			if(this.tok[i] instanceof Num){
				this.nums.push((Num)this.tok[i]);
				continue;
			}
			else {
				if(this.tok[i] instanceof Pi){
					this.nums.push(new Num(Math.PI));
					continue;
				}
				if(this.tok[i] instanceof Eul){
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
	// Verarbeitet ein Operator-Token: Poppt Operanden vom Stack, wendet operatorische Rechnung an und liefert das Zwischenergebnis.
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
    	// Verarbeitet Funktions-Token (Sin, Cos, Tan, Log, Sqrt, Ln): wendet die jeweilige Java-Math-Funktion auf den Stack-Wert an.
	public double func(Token t){
		if(t instanceof Sin){
			double cont = this.nums.pop().value();
			return Math.sin(cont);
			}
		if(t instanceof Tan){
			double cont = this.nums.pop().value();
			return Math.tan(cont);
			}
		if(t instanceof Cos){
			double cont = this.nums.pop().value();
			return Math.cos(cont);
			}
		if(t instanceof Log){
			double right = this.nums.pop().value();
			double left = this.nums.pop().value();
			return Math.log(left) / Math.log(right);
			}
		if(t instanceof Sqrt){
			double cont = this.nums.pop().value();
			return Math.sqrt(cont);
			}
		if(t instanceof Ln){
			double cont = this.nums.pop().value();
			return Math.log(cont);
		}
		else{
			throw new IllegalArgumentException("Token Unbekannt" + t);
		}
	}
}

class Plotter{
	
	Turtle t;
	double scaleX;
	double scaleY;
	ArrayList<Coord> coord_left = new ArrayList<>();
	ArrayList<Coord> coord_right = new ArrayList<>();

	// die hauptachsen und Linien zu den Werten wurden gehardcoded damit es etwas übersichtlicher erscheint
    	// Initialisiert Turtle, zeichnet die Hauptachsen und startet Koordinatensystem- sowie Funktionszeichnung
	public Plotter(Token[] arithmetic_tokens,double scaleX,double scaleY){	
		this.t = new Turtle(0, 200, 0, 50, 100, 25, 0);
	// Hardcode-Turtle
	
		this.t.width(0.5);
		this.t.push()
			.forward(100)
			.pop()
			.push()
			.right(90)
			.forward(25)
			.pop()
			.push()
			.left(90)
			.forward(25)
			.pop()
			.push()
			.backward(100)
			.pop()
			.penUp();
	// Hardcode-Turtle
		this.t.width(0.1)
			.push();
		this.cooSys(scaleX,scaleY);
		this.drawFunc(arithmetic_tokens);
	}

    	// Erstellt ein Koordinatensystem mit der angegebenen Skalierung für X- und Y-Achse
	// left_right_build
	
	public void cooSys(double scaleX,double scaleY){
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;

		double i_right = scaleX;
		double i_left = -scaleX;
		double j_left = scaleY;
		double j_right = -scaleY;
		//erstellt zu erst die vertikalen dann die horizontalen linien
		while(i_right <= 100){
			
			this.vertln(i_right);
			this.vertln(i_left);
			i_right+=scaleX;
			i_left-=scaleX;
		}

		while(j_right <= 25){
			this.horiln(j_right);
			this.horiln(j_left);
			j_right+=scaleY;
			j_left-= scaleY;
		}
		
	}

	// left_right_build
	// Zeichnet eine vertikale Markierung bei der X-Position spX und fügt die Beschriftung hinzu.
	// Passt ebenfalls die Schriftgröße an
	public void vertln(double spX){
			String px = "1.5px";
			if(this.scaleX < 5 && this.scaleX > -5){px = "0.5px";}
			this.t.penUp()
				.push()
				.forward(spX)
				.left(90)
				.push()
				.backward(0.25)
				.text(""+(spX/this.scaleX)+"",px+" Arial")
				.pop()
				.forward(50)
				.penDown()
				.backward(100)
				.pop()
				.penUp();
	}

	public void horiln(double spY){ // scaleParameterY zeigt an, wann nun ein horizontaler Strich gezogen werden soll
			String px = "1.5px";
			if(this.scaleY < 3 && this.scaleY > -3){px = "0.5px";}

			this.t.penUp()
				.push()
				.left(90)
				.forward(spY)
				.push()
				.right(90)
				.forward(0.25)
				.text(""+(spY/this.scaleY)+"",px+" Arial")
				.pop()
				.left(90)
				.forward(100)
				.penDown()
				.backward(200)
				.pop()
				.penUp();
	}
	
	// Zeichnet einen kurzen Punkt bei den übergebenen Koordinaten (x,y)
	public void coordpoints(double x, double y){
			this.t.penUp()
				.push()
				.penUp()
				.forward(x)
				.left(90)
				.forward(y-0.05)
				.penDown()
				.forward(0.1)
				.penUp()
				.pop()
				.penUp();
	}

	// Berechnet Funktionspunkte für positive und negative x, speichert sie und zeichnet die Kurve 
	// Wichtig sie zeichnet nach links und nach rechts von (0|0)
	// PlotterLines
	
	public void drawFunc(Token[] token){ // zeichnet die Funktion
		this.t.penUp()
			.width(0.1)
			.color(255,100,100)
			.push();
	// Menge an Punkten -> Linien
	
		for(double i = 0, k = 0;i < (100.0/this.scaleX);i+=0.01,k-=0.01){ //<--Menge an gezeichneten Punkten/Linien
	// Menge an Punkten -> Linien
				double j = this.findY(token,i); // berechnet y bei x = i
				double p = this.findY(token,k);
				this.coord_left.add(new Coord((j*this.scaleY),(i*this.scaleX)));
				this.coord_right.add(new Coord((p*this.scaleY),(k*this.scaleX)));
				
				this.coordpoints((i*this.scaleX),(j*this.scaleY));
				this.coordpoints((k*this.scaleX),(p*this.scaleY));
				}
			Lines l = new Lines(this.t,this.coord_right,this.coord_left);
			this.t = l.newTurtle;

		}
	// Ersetzt Ident-Tokens durch den aktuellen x-Wert, wertet den Ausdruck mit CalcUPN aus und liefert y
	public double findY(Token[] tok,double x){
		Token[] newt = new Token[tok.length];

		for(int k = 0; k<tok.length;k++){
			if(tok[k] instanceof Ident){
				newt[k]=new Num(x);
			}
			else{
				newt[k] = tok[k];
			}
		}
		CalcUPN cU = new CalcUPN(newt);
		return cU.sol;
	}
	// PlotterLines
}

class UPNParser{
	
	private ArrayList<Token> t;

	// Initialisiert den Parser mit dem Token-Array und speichert sie in einer internen Liste
	public UPNParser(Token [] tok){

		this.t = new ArrayList<>(Arrays.asList(tok));
		
	}

	// Wandelt die Token in umgekehrter polnischer Notation rekursiv in einen Ausdrucksbaum (Expr) um
	public Expr parse(){
		
		var z = this.t.removeLast();

		return switch(z){
			
				case Num n -> new Cnst(n.value());
				case Ident s -> new Va(s.name());
				case Op o -> {	var p = this.OpConv(o); // bei dem lesen eines operators wird eine neue BOp instanz erstellt
						var r = this.parse();	// mit den letzten beiden werten in der ArrayList
						var l = this.parse();
						yield new BOp(p,l,r);
						}
				
				// die trigonometrischen Records erwarten eine Liste, mit einem Elemtent entahlten ,als 2. Argument
				case Sin() -> new Func(Funcs.SIN, List.of(this.parse()));
				case Cos() -> new Func(Funcs.COS, List.of(this.parse()));
				case Tan() -> new Func(Funcs.TAN, List.of(this.parse()));
				case Ln() -> new Func(Funcs.LN, List.of(this.parse()));
				case Sqrt() -> new Func(Funcs.SQRT, List.of(this.parse()));
				case Pi() -> new Cnst(Math.PI);
				case Eul() -> new Cnst(Math.E);
				case Log() -> {Token[] newtk = new Token[this.t.size()-2];
						for(int i = 0; i<this.t.size()-2;i++){newtk[i] = this.t.get(i);};
						yield new Func(Funcs.LOG, List.of(this.parse(),this.parse()));} // der Logarithmus erwartet 2 Listenelemnte 
				default -> throw new IllegalArgumentException("Fehler in Token");

			};
	}
	
	// Übersetzt ein Operator-Token in das interne BinOp-Enum für binäre Operationen
	public BinOp OpConv(Op o){
		return switch(o){
			case Op.ADD -> BinOp.ADD;
			case Op.SUB -> BinOp.SUB;
			case Op.MUL -> BinOp.MUL;
			case Op.DIV -> BinOp.DIV;
			case Op.POW -> BinOp.POW;
			default -> throw new IllegalArgumentException("wie auch immer das möglich ist");
		};
	}


}

record Tan() implements Token{
	@Override
	public String toString(){
		return "tan";
	}
}

record Coord(double y,double x){}

sealed interface Token permits Num,Op,Sp,Tf,Ident,Sqrt,Sin,Tan,Cos,Pow,Log,Pi,Eul,Space,Ln {}

record Sqrt() implements Token{
	@Override
	public String toString(){
		return "√";
	}
}

enum Tf implements Token{
	SIN,
	COS,
	TAN,
}

enum Sp implements Token{
	CLOSED,
	OPEN,
	KOMMA
}

record Space() implements Token{}

record Num(double value) implements Token{
	public double getValue(){
		return this.value();
	}

	@Override
	public String toString(){
		return ""+this.value()+"";
	}
}

record Pi() implements Token{
	@Override
	public String toString(){
		return "π";
	}
}

record Ident(String name) implements Token{
	@Override
	public String toString(){
		return this.name();
	}
}

enum Op implements Token{
	ADD,
	SUB,
	DIV,
	MUL,
	MOD,
	POW;

@Override
    public String toString() {
        switch (this) {
            case ADD:
                return "+";    // Für Addition
            case SUB:
                return "-";    // Für Subtraktion
            case MUL:
                return "*";    // Für Multiplikation
            case DIV:
                return "/";    // Für Division
            case POW:
                return "^";    // Für Potenzierung
            default:
                throw new IllegalArgumentException("Unbekannter Operator: " + this);
        }
    }
}

record Ln() implements Token{

@Override
	public String toString(){
		return "ln";
	}

}

record Sin() implements Token {
	@Override
	public String toString(){
		return "sin";
	}
}

record Pow() implements Token{}

record Log() implements Token{
	@Override
	public String toString(){
		return "log";
	}
}

record Eul() implements Token{
	@Override
	public String toString(){
		return "e";
	}
}

record Cos() implements Token{
	@Override
	public String toString(){
		return "cos";
	}
}

class Tokenizer{

	Token[] tk;
	int pointer = 0;

	// Liest alle folgenden Token bis zum Ende, entfernt Space-Tokens und liefert das finale Token-Array

	public Token[] maker(){
		ArrayList<Token> tokens = new ArrayList<>();
		
		Token f;
		
		while ((f = this.next()) != null){
			if(!(f instanceof Space)){
				tokens.add(f);
			}
		}
	
		this.tk = tokens.toArray(new Token[0]);
		
		return this.tk;
	}

	ArrayList<Character> s = new ArrayList<>();

	// Initialisiert den Tokenizer mit einem Eingabestring und speichert alle Zeichen in einer Liste

	public Tokenizer(String b){
		
		for (char cc : b.toCharArray()) { // süeichert string in Arraylist
            		this.s.add(cc);
        	}

	}

	// Ermittelt, wie viele Zeichen zu einer Zahl gehören (inklusive eines Dezimalpunkts) und prüft auf Mehrfachpunkte

	public int numend(){
		boolean val = false;
		for(int i = 0; i< this.s.size();i++){
			if (this.s.get(i) == '.'){
				if(val){
					throw new IllegalArgumentException("zu viele Kommas in einer Zahl"); // zeigt an ob es mehr als ein "Komma" gibt
				}
				val = true;
				continue;
			}
			if (this.s.get(i) < '0' || this.s.get(i) > '9'){ // schaut wie lang die Zahl ist
				return i;		
			}
		}
		return this.s.size(); // anstonsten ist die Zahl alles was es im string gibt.
	}	

	// Ermittelt die Länge eines Identifikators, also zusammenhängende Buchstaben am Listenanfang

	public int identend(){
		boolean val = false;
			for(int i = 0; i< this.s.size();i++){
				if ((this.s.get(i) < 'a' || this.s.get(i) > 'z') && (this.s.get(i) < 'A' || this.s.get(i) > 'Z')){
					return i;		
				}
			}
		return this.s.size();
	}

	// Gibt das nächste Token zurück: Zahl, Operator, Separator, Space oder Ident/Pi und entfernt es aus der Liste

	public Token next(){
		if(this.s.isEmpty()){return null;} // die Arraylist ist irgenwann leer
		if(this.s.get(0) >= '0' && this.s.get(0) <= '9'){  	
			int i = this.numend();
			List<Character> ss = this.s.subList(0,i);	// Wenn eine Ziffer gelesen wird dann wandeltt er sie aus dem char in einen string
			String a = "";					// um um es anschließend in parsedouble verwenden zu können.

			for(char s : ss){a+=s;}

			double d = Double.parseDouble(a);
			
			for(int k = 0;k<i;k++){this.s.remove(0);}       // es wird der erste Token schließlich entfernt
			
			return new Num(d);
		}

		// durch early exit und die if-statements kommt der Rest auch nur dann dran wenn der Token keine zahl ist 

		var n = switch (this.s.get(0)){ // in diesem Teil wird nur der Operator oder Seperator ausgegeben durch das swithc statement
			
			case '+' -> Op.ADD;
			case '-' -> Op.SUB;
			case '/' -> Op.DIV;
			case '*' -> Op.MUL;
			case '%' -> Op.MOD;
			case '^' -> Op.POW;
			case ')' -> Sp.CLOSED;
			case '(' -> Sp.OPEN;
			case ',' -> Sp.KOMMA;
			case ' ' -> new Space(); // <--------- Hier steht der case für die spacetaste
			default -> this.charFinder();
		};

		if(!this.s.isEmpty()){
			this.s.remove(0);
		}

		return n;
	}

	// Identifiziert mehrbuchstabige Namen oder π (pi optisch schwer erkennlich) und gibt das passende Token zurück

	public Token charFinder(){
		if(this.s.get(0) >= 'a' && this.s.get(0) <= 'z' || 
				   this.s.get(0) >= 'A' && this.s.get(0) <= 'Z'){
					int j = this.identend();
					String name ="";
					for(int i = 0; i<j;i++){
						name += this.s.get(i);
					}
					this.s.subList(0,j-1).clear();
					return new Ident(name);
				}
				else{
					if(this.s.get(0) == 'π'){return new Pi();}
					throw new IllegalArgumentException("Probleme in Chrafinder");
			}
	}
}

class FunctionResolver {

	static String Expr= "";

	// Wandelt ein Ident-Token in das passende Funktions- oder Konstanten-Token um, andere Tokens bleiben unverändert
	
	public static Token resolve(Token token) {
		if (token instanceof Ident identToken) {
		String name = identToken.name().toLowerCase();
		return switch (name) {
			case "sin" -> new Sin();
			case "cos" -> new Cos();
			case "tan" -> new Tan();
			case "sqrt" -> new Sqrt();
			case "log" -> new Log();
			case "ln" -> new Ln();
			case "e" -> new Eul();
			case "π" -> new Pi();
			default -> token;
			};
		}
		return token;
	}

	// Löst alle Tokens nacheinander auf, baut daraus einen Ausdrucksstring auf und speichert diesen in Expr
	public static Token[] resolveAll(Token[] tokens) {
		List<Token> result = new ArrayList<>();
		String s = "";
		for (Token token : tokens) {
			var R = resolve(token);
			result.add(R);
			s += stringify(R);
						
		}
	putIn(s);
	Expr = s;
	return result.toArray(new Token[0]);
    }
	// Gibt den zuletzt erzeugten Ausdrucksstring zurück
	public String getExpr(){
		return this.Expr;
	}
	
	// Speichert den übergebenen Ausdrucksstring in der statischen Variable Expr
	public static void putIn(String s){
		Expr = s;
	}

	// Erzeugt die textuelle Darstellung eines Tokens, inklusive Funktionsnamen und Leerzeichen
	public static String stringify(Token t){
		return switch(t){
					case Num n -> String.valueOf(n.value())+ " ";
					case Op.ADD -> "+ ";
					case Op.SUB -> "- ";
					case Op.MUL -> "* ";
					case Op.DIV -> "/ ";
					case Op.POW -> "^ ";
					case Sp.OPEN -> "( ";
					case Sp.CLOSED -> ") ";
					case Sp.KOMMA -> ", ";
					default -> 
						{if(t instanceof Sin){yield "sin ";}
						if(t instanceof Cos){yield "cos ";}
						if(t instanceof Tan){yield "tan ";}
						if(t instanceof Sqrt){yield "sqrt ";}
						if(t instanceof Log){yield "log ";}
						if(t instanceof Ln){yield "ln";}
						if(t instanceof Pi){yield "π ";}
						if(t instanceof Eul){yield "e ";}
						if(t instanceof Ident) yield ((Ident)t).name();
						else{throw new IllegalArgumentException("Nicht erkannter Token");}}
			};

	}
}

static class CalcTree{

	Expr e;

	public CalcTree(Expr E){
		this.e = E;
		}

	public double calc(){

		return switch(this.e){
			case Cnst k -> k.cnst();
			case BOp b -> b.operation();
			case Func fun -> fun.function();
			default -> throw new IllegalArgumentException("CalcFehler");
		};

	}

}

class Inf2Upn{

	Stack<Token>operatorStack;

	Token[] outPutToken;
	
	// Initialisiert den Shunting-Yard-Algorithmus (online-pseudocode-rausgesucht): konvertiert Infix-Tokens in UPN und füllt outPutToken
	// jedesmal wenn eine Konstante oder ein identifier gelesen wird diese in einer operator ArrayList gespeichert
	// bei einer Funktion wird diese auf einen seperaten operatorStack gepusht und wie ein Operaot behandelt
	// wenn ein Binärer operator gelesen wird , so wird die priorität des letzten operator gelsen und entschieden ob der letzte operator in die outputqueu
	// "geadded" wird. Bei Klammern wird wird eine innere schleife nach Prinzip der gesamten Methode wieder auf den Stack pusht un popt und der arrayList hinzufügt
	//
	public Inf2Upn(Token[] tokens){

		this.operatorStack = new Stack<>();
		ArrayList<Token> outputQueue = new ArrayList<>();

		for(int i = 0; i<tokens.length;i++){

			Token tk = tokens[i];

			if(tk instanceof Num || tk instanceof Ident || tk instanceof Eul || tk instanceof Pi){
				outputQueue.add(tk);
			}
			if(tk instanceof Sin || tk instanceof Cos || tk instanceof Tan || tk instanceof Sqrt || tk instanceof Log || tk instanceof Ln){
				this.operatorStack.add(tk);
			}
			if(tk instanceof Op){
				while(!this.operatorStack.isEmpty() && 
					(this.operatorStack.peek() instanceof Sin ||
					 this.operatorStack.peek() instanceof Cos ||
					 this.operatorStack.peek() instanceof Tan ||
					 this.operatorStack.peek() instanceof Sqrt||
					 this.operatorStack.peek() instanceof Log ||
					 this.operatorStack.peek() instanceof Op ||
					 this.operatorStack.peek() instanceof Ln)
						&& this.compare(this.operatorStack.peek(),tk)){
				
					outputQueue.add(this.operatorStack.pop());
				}
				this.operatorStack.add(tk);
			}
			if(tk instanceof Space){continue;}		// wenn ein (Space)" " gelesn wird wird kein fehler geworfen sondern einfach weitergelesen
			if(tk == Sp.OPEN){
				this.operatorStack.add(tk);
			}
			if(tk == Sp.CLOSED){
				while(!this.operatorStack.isEmpty() && this.operatorStack.peek()!= Sp.OPEN){
					outputQueue.add(this.operatorStack.pop());
				}
				if(this.operatorStack.isEmpty()){
					throw new IllegalArgumentException("Fehlende Klammer");
				}
				this.operatorStack.pop();
				if(!this.operatorStack.isEmpty() && 
					(this.operatorStack.peek() instanceof Sin ||
					 this.operatorStack.peek() instanceof Cos ||
					 this.operatorStack.peek() instanceof Tan ||
					 this.operatorStack.peek() instanceof Sqrt||
					 this.operatorStack.peek() instanceof Log ||
					 this.operatorStack.peek() instanceof Ln)){
					outputQueue.add(this.operatorStack.pop());
				}
			}
		}

		while(!this.operatorStack.isEmpty()){
			if(this.operatorStack.peek() instanceof Sp){
				throw new IllegalArgumentException("Fehlende Klammer");
			}
			outputQueue.add(this.operatorStack.pop());
		}

		this.outPutToken = new Token[outputQueue.size()];

		// Am Ende wird die ArrayList in ein Array überführt

		for(int i = 0; i<outputQueue.size();i++){
			this.outPutToken[i]  =  outputQueue.get(i);
		}
	}
	
	// compare vergleicht die Priorität von 2 Token

	public boolean compare(Token StT, Token tk){
		if(prio(StT) >= prio(tk)){
			return true;
		}
		else{
			return false;
		}
		
	}

	// prio setzt die Prioritäten der Operatoren/Funktionen
	public int prio(Token t){
		return switch(t){
			case Op.ADD -> 10;
			case Op.SUB -> 10;
			case Op.MUL -> 20;
			case Op.DIV -> 20;
			case Op.POW -> 30;
			case Sp.OPEN -> 50;
			case Sp.CLOSED -> 50;
			case Num n -> throw new IllegalArgumentException();
			default -> 45;
		};
	}
}
