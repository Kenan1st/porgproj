package project.tok.Model;

public record Sqrt() implements Token{
	@Override
	public String toString(){
		return "√";
	}
}
