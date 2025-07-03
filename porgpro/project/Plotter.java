package project;

import lvp.Clerk;
import lvp.skills.Text;
import lvp.skills.Interaction;
import lvp.views.Dot;
import lvp.views.Turtle;

public class Plotter{
	
	Turtle t;
	double vertp = 0.0;
	double horip = 0.0;

	public Plotter(){				// Der Konstruktor erstellt eine Turtle mit den Basis Achsen
		this.t = new Turtle(0, 200, 0, 50, 100, 25, 0);
		
		for(int i = 0; i<2;i++){// Dicke der Linien
		t.push()
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
		}
		this.cooSys(1.0,1.0);
	}

	public void cooSys(double scaleX,double scaleY){ // erstellt ein Kooridnatensystem mit skallierung
		double i = -100;
		double j = -50;

		while((i >= -100 && i <= 100) && (j >= -50 && j <= 50)){
			this.vertln(i);
			this.horiln(j);
			
			i=i+scaleX;
			j=j+scaleY;
		}
		
	}

	public void vertln(double spX){ // scaleparameterX zeigt an, wann nun ein vertikaler Strich gezogen werden soll
			this.t.penUp()
				.push()
				.backward(spX)
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
				.backward(spY)
				.left(90)
				.forward(25)
				.penDown()
				.backward(50)
				.pop();
	}

	public void drawFunc(Token[] t){ // zeichnet die Funktion
		this.t.penUp()		 // setzt den Stift bei dem letzten -Y Wert
			.right(90)
			.forward(25)
			.left(90)
			.push();

		for(double i = -100.0;i < 100.0;i+=0.1){
			double j = this.findY(t,i); // berechnet y bei x = i

			this.t.penUp()
				.push()
				.forward(i)
				.left(90)
				.forward(j+24.025)
				.penDown()
				.forward(0.05)
				.penUp()
				.pop();
			}
		}

	public double findY(Token[] t,double x){

		Token[] newt = new Token[t.length];

		for(int k = 0; k<t.length;k++){
			if(t[k] instanceof Ident){
				newt[k]=new Num(x);
			}
			else{
				newt[k] = t[k];
			}
		}

		UPNParser UP = new UPNParser(newt);
		Expr g = UP.parse();
		CalcTree c = new CalcTree(g);
		return c.calc();
	}
}

