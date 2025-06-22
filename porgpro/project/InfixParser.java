package project;

import project.tok.Model.*;
import project.tok.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

public class InfixParser{

	Deque<Token> deque = new ArrayDeque<>();
	
	public InfixParser(Token [] tok){
	
		for(Token tk : tok){
			switch(tk){
				case Num n-> deque.add(tk);
				case Op.ADD ->{
					Token tmp = deque.removeLast();
					deque.add(Op.ADD);
					deque.add(tmp);
					}
				case Op.SUB ->{
					Token tmp = deque.removeLast();
					deque.add(Op.SUB);
					deque.add(tmp);
					}
				case Op.MUL ->{
					Token tmp = deque.removeLast();
					deque.add(Op.MUL);
					deque.add(tmp);
				}
				case Op.DIV -> {
					Token tmp = deque.removeLast();
					deque.add(Op.DIV);
					deque.add(tmp);
				}
				case Op.POW -> {
					Token tmp = deque.removeLast();
					deque.add(Op.POW);
					deque.add(tmp);
				}
				default -> {
				if(tk instanceof EUL) { deque.add(new EUL());}
				if(tk instanceof PI) { deque.add(new PI());}
				if(tk instanceof LOG){
					Token tmp_1 = deque.removeLast();
					Token tmp_2 = deque.removeLast();
					deque.add(new LOG());
					deque.add(tmp_1);
					deque.add(tmp_2);
				}
				if(tk instanceof SIN){ deque.addFirst(new SIN());}
				if(tk instanceof TAN){ deque.addFirst(new TAN());}
				if(tk instanceof COS){deque.addFirst(new COS());}
					else{ deque.addLast(new Fehlsch());}
				//throw new IllegalArgumentException("falsch");
				}
			};
		}
	
	}
}
