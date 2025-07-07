import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import lvp.Clerk;
import lvp.skills.Interaction;
import lvp.skills.Text;
import lvp.views.Dot;
import lvp.views.Turtle;


void main(){

 Clerk.clear();

    String exampleValue = "5 6 + x 3 ^ * x 2 ^ - x +"; // Input Example
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

	Plotter PO = new Plotter(Z);

	/*System.out.println(PO.coord.get(0));
	System.out.println(PO.coord.get(PO.coord.size()-1));
	System.out.println(PO.coord.size());
	*/
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



class Lines{
	Turtle newTurtle;
	double x_s_left;
	double y_s_left;
	double x_s_right;
	double y_s_right;
	ArrayList<Coord> lstCoords_left;
	ArrayList<Coord> lstCoords_right;
	double [] lengths_left;
	double [] lengths_right;
	
	public Lines(Turtle turtle, ArrayList<Coord> oldlstCoords_left, ArrayList<Coord> oldlstCoords_right){
		this.lstCoords_left = oldlstCoords_left;
		this.lstCoords_right = oldlstCoords_right;
		this.newTurtle = turtle;
		
		this.x_s_left = this.lstCoords_left.get(0).x();
		this.y_s_left = this.lstCoords_left.get(0).y();
		this.y_s_right = this.lstCoords_right.get(0).y();
		this.x_s_right = this.lstCoords_right.get(0).x();

		this.drawLines();
		}

	public void drawLines(){
	
		for(int i = 1; i<this.lstCoords_left.size();i++){
				Coord nextCoord_right = this.lstCoords_left.get(i);
				Coord nextCoord_left = this.lstCoords_right.get(i);
	
				
				this.lengths_left = calcLengths(this.x_s_left, nextCoord_left.x(), this.y_s_left, nextCoord_left.y());
				this.lengths_right = calcLengths(this.x_s_right, nextCoord_right.x(), this.y_s_right, nextCoord_right.y());

				this.newTurtle.push()
						.penUp()
						.forward(this.x_s_left)
						.left(90)
						.forward(this.y_s_left)
						.left(this.lengths_left[1])
						.penDown()
						.forward(this.lengths_left[0])
						.pop();
				
				this.newTurtle.push()
						.penUp()
						.forward(this.x_s_right)
						.left(90)
						.forward(this.y_s_right)
						.right(this.lengths_right[1])
						.penDown()
						.forward(this.lengths_right[0])
						.pop();
						
				this.x_s_left= nextCoord_left.x();
				this.y_s_left = nextCoord_left.y();
				this.y_s_right = nextCoord_right.y();
				this.x_s_right = nextCoord_right.x();
		}
	}

	public double[] calcLengths(double x_0,double x_1, double y_0,double y_1){
			
			double delt_x = (x_0-x_1);
			double delt_y = (y_0-y_1);

			double hyp = Math.sqrt(
					Math.pow(delt_x,2.0)+
					Math.pow(delt_y,2.0)
					);
			double degree = Math.atan2(delt_y,delt_x);

		return new double[]{hyp,degree};
	}
}




record E() implements Expr{}



enum UOp implements Expr{
	POS,
	NEG;

	public double apply(double d){
		return switch(this){
			case POS -> d;
			case NEG -> -d;
		};
	}
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
		CalcTree c = new CalcTree(this.e.get(0));
		
		double cup = c.calc();
		double cup_1;

		if(this.e.size() >= 2){

		CalcTree c_1 = new CalcTree(this.e.get(1)); 
		cup_1 = c_1.calc();
		return trans(cup,cup_1);
		}
		else{
			return trans_1(cup);
		}
		
	}

	public double trans(double d, double d_1){
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
			case Funcs.LOG -> Math.log(d);
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
			case BinOp.ADD -> this.expConv(this.e_1()) + this.expConv(this.e_2());
			case BinOp.SUB -> this.expConv(this.e_1()) - this.expConv(this.e_2());
			case BinOp.MUL -> this.expConv(this.e_1()) * this.expConv(this.e_2());
			case BinOp.DIV -> this.expConv(this.e_1()) / this.expConv(this.e_2());
			case BinOp.POW -> Math.pow(this.expConv(this.e_1()),this.expConv(this.e_2()));
			default -> throw new IllegalArgumentException("Fehler in BOp");
		};
	}

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
				throw new IllegalArgumentException("Fehler in Func");
		}
		}
		else{
			return ((Cnst)e).cnst();
		}
	}

}






class TreeMaker{

	public int k;

	public TreeMaker(){
		this.k = 0;
	}

	public String planter(Expr e_in){
		
		String connects = "";

		if(e_in instanceof BOp){
			
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

			connects += "n"+this.k+"[label=\""+((Func)e_in).f() + "\"] ;\n";
			//this.k += 1;

			connects += "n"+this.k+ " -> " +"n"+ down + ";\n";
		}
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

	public Plotter(Token[] arithmetic_tokens){				// Der Konstruktor erstellt eine Turtle mit den Basis Achsen
		this.t = new Turtle(0, 200, 0, 50, 100, 25, 0);
		
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
		this.t.width(0.1);
		this.cooSys(5,5);
		this.drawFunc(arithmetic_tokens);
	}


	public void cooSys(double scaleX,double scaleY){ // erstellt ein Kooridnatensystem mit skallierung
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;

		double i_right = scaleX;
		double i_left = -scaleX;
		double j_left = scaleY;
		double j_right = -scaleY;

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


	public void vertln(double spX){ // scaleparameterX zeigt an, wann nun ein vertikaler Strich gezogen werden soll
			this.t.penUp()
				.push()
				.forward(spX)
				.left(90)
				.forward(50)
				.penDown()
				.backward(100)
				.pop();
	}

	public void horiln(double spY){ // scaleParameterY zeigt an, wann nun ein horizontaler Strich gezogen werden soll
			this.t.penUp()
				.push()
				.left(90)
				.forward(spY)
				.left(90)
				.forward(100)
				.penDown()
				.backward(200)
				.pop();
	}

	
	public void coordpoints(double x, double y){
			this.t.push()
				.penUp()
				.forward(x)
				.left(90)
				.forward(y-0.05)
				.penDown()
				.forward(0.1)
				.penUp()
				.pop();
	}

	public void drawFunc(Token[] token){ // zeichnet die Funktion
			this.t.width(0.25)
				.color(255,100,100)
				.push();

			for(double i = 0, k = 0;i < (100.0/this.scaleX);i+=0.1,k-=0.1){

				double j = this.findY(token,i); // berechnet y bei x = i
				double p = this.findY(token,k);
				this.coord_right.add(new Coord((j*this.scaleY),(i*this.scaleX)));
				this.coord_left.add(new Coord((p*this.scaleY),(k*this.scaleX)));
				
				this.coordpoints((i*this.scaleX),(j*this.scaleY));
				this.coordpoints((k*this.scaleX),(p*this.scaleY));

			Lines l = new Lines(this.t,this.coord_left,this.coord_right);
			this.t = l.newTurtle;

		}
	}

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
}

class UPNParser{
	
	private ArrayList<Token> t;

	public UPNParser(Token [] tok){

		this.t = new ArrayList<>(Arrays.asList(tok));
		
	}

	public Expr parse(){
		
		var z = this.t.removeLast();

		return switch(z){
			
				case Num n -> new Cnst(n.value());
				case Ident s -> new Va(s.name());
				case Op o -> {	var p = this.OpConv(o);
						var r = this.parse();
						var l = this.parse();
						yield new BOp(p,l,r);
						}
				case Sin() -> new Func(Funcs.SIN, List.of(this.parse()));
				case Cos() -> new Func(Funcs.COS, List.of(this.parse()));
				case Tan() -> new Func(Funcs.TAN, List.of(this.parse()));
				case Log() -> new Func(Funcs.LOG, List.of(this.parse()));
				case Sqrt() -> new Func(Funcs.SQRT, List.of(this.parse()));
				case Pi() -> new Cnst(Math.PI);
				case Eul() -> new Cnst(Math.E);
				case Ln() -> new Func(Funcs.LN, List.of(this.parse()));
				default -> throw new IllegalArgumentException("Fehler in Token");

			};
	}

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

	public Tokenizer(String b){
		
		for (char cc : b.toCharArray()) { // süeichert string in Arraylist
            		this.s.add(cc);
        	}

	}

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

	public int identend(){
		boolean val = false;
			for(int i = 0; i< this.s.size();i++){
				if ((this.s.get(i) < 'a' || this.s.get(i) > 'z') && (this.s.get(i) < 'A' || this.s.get(i) > 'Z')){
					return i;		
				}
			}
		return this.s.size();
	}

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

	public static Token[] resolveAll(Token[] tokens) {
		List<Token> result = new ArrayList<>();
		String s = "";
		for (Token token : tokens) {
			var R = resolve(token);
			result.add(R);
			s += stringify(R);
						
		}
	putin(s);
	Expr = s;
	return result.toArray(new Token[0]);
    }

	public String getExpr(){
		return this.Expr;
	}

	public static void putin(String s){
		Expr = s;
	}

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


