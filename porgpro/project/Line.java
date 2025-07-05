package project;

import project.tok.Model.*;
import project.tok.*;
import project.ast.*;



class Line{
	Turtle newTurtle;
	double x_s;
	double y_s;
	ArrayList<Coord> lstCoords;
	
	public Line(Turtle turtle, ArrayList<Coord> oldlstCoords){
		this.lstCoords = oldlstCoords;
		this.newTurtle = turtle;
		
		this.x_s = ((Coord)this.lstCoords.get(0)).x();
		this.y_s = ((Coord)this.lstCoords.get(0)).y();
		this.drawLines();
		}

	public void drawLines(){

		this.newTurtle.penUp()
				.forward(x_s)
				.left(90)
				.forward(y_s)
				.push();
			
		for(int i = 1; i<this.lstCoords.size();i++){
				Coord nextCoord = this.lstCoords.get(i);
	
				double ank_gek = ((this.x_s - nextCoord.x()) / (this.y_s - nextCoord.y()));
				double hyp = Math.sqrt(Math.pow((this.x_s - nextCoord.x()),2)+
					Math.pow((this.y_s - nextCoord.y()),2));
				double degree = Math.atan(ank_gek);

				this.newTurtle.left(degree)
						.penDown()
						.forward(hyp)
						.push();
				this.x_s = nextCoord.x();
				this.y_s = nextCoord.y();
		}
	}
}

