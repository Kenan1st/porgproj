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
			this.t.width(0.1)
				.color(255,100,100)
				.push();

			for(double i = 0, k = 0;i < (100.0/this.scaleX);i+=0.1,k-=0.1){

				double j = this.findY(token,i); // berechnet y bei x = i
				double p = this.findY(token,k);
				this.coord_left.add(new Coord((j*this.scaleY),(i*this.scaleX)));
				this.coord_right.add(new Coord((p*this.scaleY),(k*this.scaleX)));
				
				this.coordpoints((i*this.scaleX),(j*this.scaleY));
				this.coordpoints((k*this.scaleX),(p*this.scaleY));

			Lines l = new Lines(this.t,this.coord_right,this.coord_left);
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

