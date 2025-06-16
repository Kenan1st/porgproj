import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

void main(String [] args){
	
		Tokenizer t = new Tokenizer("46546.78672783+378648%038928");
		System.out.println(Arrays.toString(t.maker()));

		Tokenizer t1 = new Tokenizer("46546.78672783");
                System.out.println(Arrays.toString(t1.maker()));

		Tokenizer t2 = new Tokenizer("+378648%038928"); // ADD, Num(378648.0), MOD, Num(38928.0)
                System.out.println(Arrays.toString(t2.maker()));

//		Tokenizer t3 = new Tokenizer("46546.7867.2783+378648%038928");
//                System.out.println(Arrays.toString(t3.maker())); // Fehler soll geworfen werden

		Tokenizer t4 = new Tokenizer("10^4+5");
		System.out.println(Arrays.toString(t4.maker()));
}

public class Tokenizer{

	Token[] tk;
	int pointer = 0;

	public Token[] maker(){

		ArrayList<Token> tokens = new ArrayList<>();  // gibt nur die gewünschte Anzahl an Tokens aus sprich alle
		while(true){
		var f = this.next();
			if(f != null){tokens.add(f);}
			else{break;}
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

	public int subend(){
		boolean val = false;
		for(int i = 0; i< this.s.size();i++){
			if (this.s.get(i) == '.'){
				if(val){
					throw new IllegalArgumentException(); // zeigt an ob es mehr als ein "Komma" gibt
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

	public Token next(){
		if(this.s.isEmpty()){return null;} // die Arraylist ist irgenwann leer
		if(this.s.get(0) >= '0' && this.s.get(0) <= '9'){  	
			int i = this.subend();
			List<Character> ss = this.s.subList(0,i);	// Wenn eine Ziffer gelesen wird das wandel er sie aus dem char in einen string
			String a = "";					// um um es anschlieén in parsedouble verwenden zu können.

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
			case '~' -> Op.NEG;
			case '%' -> Op.MOD;
			case '^' -> Op.POW;
			case ')' -> Sp.CLOSED;
			case '(' -> Sp.OPEN;
			default -> throw new IllegalArgumentException();
		};
		
		this.s.remove(0);

		return n;
	}

	public Token peek(int i){
		this.pointer = i;
		return this.tk[i];
	}

	public Token peek(){
		return this.tk[this.pointer+1];
	}
}

sealed interface Token permits Num,Op,Sp {
}

enum Op implements Token{
	ADD,
	NEG,
	SUB,
	DIV,
	MUL,
	MOD,
	POW
}

enum Sp implements Token{
	CLOSED,
	OPEN
}

record Num(double value) implements Token{}
