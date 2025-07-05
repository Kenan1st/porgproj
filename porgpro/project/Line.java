package project;

import project.tok.Model.*;
import project.tok.*;
import project.ast.*;


public class Line{

	Turtle newTurtle;
	double x_s;
	double y_s;
	ArrayList<Coord> lstCoords;
	
	public Line(Turtle turtle, ArrayList<Coord> lstCoords){
		this.lstCoords = lstCoords;
		this.newTurtle = turtle;
		
		this.x_s = lstCoords.get(0).x();
		this.y_s = lstCoords.get(0).y();
		this.drawLine();
		}

	public void drawLine(){

		this.newTurtle.penUp()
				.forward(x_s)
				.left(90)
				.forward(y_s)
				.penDown()
				.push();
			
		for(int i = 1; i<lstCoords.size();i++){
				Coord nextCoord = this.lstCoords.get(i);
	
				double delt_x_y = (this.x_s - nextCoord.x())/
						 (this.y_s - nextCoord.y());
				double degree = Math.atan(delt_x_y);
				this.newTurtle.left(degree)
						.forward(delt_x_y)
						.push();
				this.x_s = nextCoord.x();
				this.y_s = nextCoord.y();
		}
	}

	public Turtle getNewTurtle(){
		return this.newTurtle;
	}
}

