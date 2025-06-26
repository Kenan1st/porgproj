package project.tok.Model;

public record Pi() implements Token{
	@Override
	public String toString(){
		return "π";
	}
}
