package project;

import project.tok.Model.*;
import project.tok.*;
import project.ast.*;




class Lines{
	Turtle newTurtle;
	double x_s_left;
	double y_s_left;
	double x_s_right;
	double y_s_right;
	ArrayList<Coord> lstCoords_left;
	ArrayList<Coord> lstCoords_right;
	
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
	
				
				double [] lengths_left = calcLengths(this.x_s_left, nextCoord_left.x(), this.y_s_left, nextCoord_left.y());
				double [] lengths_right = calcLengths(this.x_s_right, nextCoord_right.x(), this.y_s_right, nextCoord_right.y());

				this.newTurtle.push()
						.penUp()
						.forward(this.x_s_left)
						.left(90)
						.forward(this.y_s_left)
						.right(lengths_left[1])
						.penDown()
						.forward(lengths_left[0])
						.pop();
				
				this.newTurtle.push()
						.penUp()
						.forward(this.x_s_right)
						.left(90)
						.forward(this.y_s_right)
						.right(lengths_right[1])
						.penDown()
						.forward(lengths_right[0])
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

			double gek_ank = (delt_y/delt_x);
			double hyp = Math.sqrt(
					Math.pow(delt_x,2.0)+
					Math.pow(delt_y,2.0)
					);
			double degree = Math.toDegrees(Math.atan(gek_ank));

		return new double[]{hyp,degree};
	}
}

