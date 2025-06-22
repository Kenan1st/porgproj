package project.tok.Model;

public record LOG() implements Token{
	@Override
	public String toString(){
		return "log";
	}
}
