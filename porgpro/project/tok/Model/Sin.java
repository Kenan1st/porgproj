package project.tok.Model;

public record Sin() implements Token {
	@Override
	public String toString(){
		return "sin";
	}
}
