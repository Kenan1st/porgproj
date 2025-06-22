package project.tok.Model;

public record SIN() implements Token {
	@Override
	public String toString(){
		return "sin";
	}
}
