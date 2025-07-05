package project;

import lvp.Clerk;
import lvp.skills.Text;
import lvp.skills.Interaction;
import lvp.views.Dot;
import lvp.views.Turtle;


public class Plotter{
	
	Turtle t;
	double scaleX;
	double scaleY;
	ArrayList<Coord> coord = new ArrayList<>();

	public Plotter(){				// Der Konstruktor erstellt eine Turtle mit den Basis Achsen
		this.t = new Turtle(0, 200, 0, 50, 100, 25, 0);
		
		this.t.width(0.5);
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
		this.t.width(0.1);
		this.cooSys(6.0,6.0);
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

		while(j_right<=25){
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

	public void drawFunc(Token[] t){ // zeichnet die Funktion
		this.t.width(0.5)
			.color(255,100,100)
			.penUp()		 // setzt den Stift bei dem letzten -Y Wert
			.right(90)
			.forward(25)
			.left(90)
			.push();

		for(double i = -(100.0/this.scaleX);i < (100.0/this.scaleX);i+=0.1){

			double j = this.findY(t,i); // berechnet y bei x = i

			this.coord.add(new Coord(j,i));


			this.t.penUp()
				.push()
				.forward(i*this.scaleX)
				.left(90)
				.forward((j*this.scaleY)+24.925)
				.penDown()
				.forward(0.075)
				.penUp()
				.pop();
			}

		Line l = new Line(this.t,this.coord);
		l.drawLine();
		this.t = l.getNewTurtle();

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

		CalcUPN cU = new CalcUPN(newt);
		
		return cU.sol;
	}
}

