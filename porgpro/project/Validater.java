class Validater{

	boolean upn = false;
	boolean inf = false;
	boolean ar_expr = true;
	

	public Validater(Token[] tokens){

		if(tokens.length < 2 && tokens[0] instanceof Ident){this.ar_expr = false;}

		if(tokens.length < 2 && tokens[0] instanceof Num){this.ar_expr = true;}

		else{
			

			if((tokens[0] instanceof Num || tokens[0] instanceof Ident || tokens[0] instanceof Eul || tokens[0] instanceof Pi) &&
			   (tokens[1] instanceof Num || tokens[1] instanceof Ident || tokens[1] instanceof Eul || tokens[1] instanceof Pi)){
				this.upn = true;

				for(Token t : tokens){
					if(t instanceof Ident){this.ar_expr = false;}
				}
			}

			else{
				this.inf = true;
				
				for(Token t : tokens){
					if(t instanceof Ident){this.ar_expr = false;}
			}
			}
		}
		return;
	}
}

