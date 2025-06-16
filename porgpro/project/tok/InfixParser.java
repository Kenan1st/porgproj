public class InfixParser{
	
	Tokenizer T;
	Token[] tok;

	public InifixParser(String s){
		this.T = new Tokenizer(s);
		this.tok = this.T.maker();
	}

	public int precedence(Token Z){
		return switch(z){
			case ADD -> 1;
			case SUB -> 1;
			case MUL -> 2;
			case DIV -> 2;
			case POW -> 3;
			default -> throw new IllegalArgumentException("kein binärer Operator");
		};
	}

	public Expr parse_expression(){
    		return parse_expression_1(parse_primary(), 0);
	}

	public Expr parse_primary(){
		
	}

	public Expr parse_expression_1(Expr lhs, int min_precedence){
    		Token lookahead = T.peek(min_precedence);
    			
		while (precedence(lookahead) >= min_precedence){
        		Token op = lookahead;
        		T.peek(min_presedence+1);
        		Expr rhs = parse_primary();
			lookahead = T.peek();
        		
			while(presedence(lookahead) > presedence(op)){
            			rhs = parse_expression_1 (rhs, (precedence(op) + (precedence(lookahead) > precedence(op) ? 1 : 0)));
// (1 if lookahead precedence is greater, else 0));
            			lookahead = T.peek();

        			lhs = the result of applying op with operands lhs and rhs;

    				return lhs;
	}
	}
}
}
