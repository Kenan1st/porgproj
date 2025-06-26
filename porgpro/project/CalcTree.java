package project;

import project.tok.Model.*;
import project.tok.*;
import project.ast.*;
import java.util.Arrays;
import java.util.ArrayList;

public class CalcTree{

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
