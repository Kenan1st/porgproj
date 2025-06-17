package project;

import project.tok.Model.*;
import project.tok.*;

public class Tokennicer{
	
	TokenString tS;
	int place;

	public Tokennicer(TokenString ts){
		
		this.tS = ts;
		ArrayList<Integer> list = this.ts.prio;
		int min_precedence = 1000;
		this.place = 0;

		for(int i = list.size()-1; i>0 ; i--){
			if(list.get(i)!= 0 && list.get(i) < min_precedence){
				min_precedence = list.get(i);
				this.place = i;
			}
		}

		// IDEE: du machst einen rekursiven aufruf in dem der String immer kleiner wird
		// und nach der dahemaligen Idee von Gideon, isst man immer ein weiteres Stück
		// der Token
	}
}
