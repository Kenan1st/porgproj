import java.util.ArrayList;

public class besser{

	public static void main (String [] args){
	
	System.out.println(con("437,482")+con("34,6387"));

	}

public static double con(String s){
        ArrayList<Character> l = new ArrayList<>();

	char [] c = s.toCharArray();
	int cnt = -1; //die stelle an der das komma liegt
	double num = 0.0; // ergebnis
	boolean pt = false; // Komma da?
	int z = 0; // zählt die Nachkommastellen

	for(int i = 0; i< s.length();i++){
		if(c[i] == ','){ 		// speichert wann das Komma auftritt
			cnt = i;
			pt = true;
			continue;}
		if(pt){z++;l.add(c[i]);}        // nachdem das Komma eintritt werden die Nachkommaziffern gezählt
		else{l.add(c[i]);}
	}

	int u = l.size(); 

	for(int i = 1; i <= u ; i++){
		double tmp = Math.pow(10,u-i);
	
		num += switch(l.get(i-1)){
			
			case '1' -> 1.0 * tmp;
			case '2' -> 2.0 * tmp;
			case '3' -> 3.0 * tmp;
			case '4' -> 4.0 * tmp;
			case '5' -> 5.0 * tmp;
			case '6' -> 6.0 * tmp;
			case '7' -> 7.0 * tmp;
			case '8' -> 8.0 * tmp;
			case '9' -> 9.0 * tmp;
			default -> throw new IllegalArgumentException();
		};
	}
	num = num * Math.pow(10,-z); //die gesamte Nummer wird ohne komma 
				     //als ganze Zahl in einem double gespeichert 
				     //und dann mit einer negativen Potenz 
				     //runterskaliert
	return num; 
}
}
