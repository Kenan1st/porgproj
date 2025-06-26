package project.tok;

import project.tok.Model.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer{

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
			default -> {
				if(this.s.get(0) >= 'a' && this.s.get(0) <= 'z' || 
				   this.s.get(0) >= 'A' && this.s.get(0) <= 'Z'){
					int j = this.identend();
					String name ="";
					for(int i = 0; i<j;i++){
						name += this.s.get(i);
					}
					this.s.subList(0,j-1).clear();
					yield new Ident(name);
				}
				else{
					throw new IllegalArgumentException();
				}
			}
		};

		if(!this.s.isEmpty()){
			this.s.remove(0);
		}

		return n;
	}

public static void main(String [] args){

	Tokenizer T1 = new Tokenizer("34 34 +");

	System.out.println(Arrays.toString(T1.maker()));
}
}
