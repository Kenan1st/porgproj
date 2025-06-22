package project.tok.Model;

public record SQRT() implements Token{
	@Override
	public String toString(){
		return "√";
	}
}
