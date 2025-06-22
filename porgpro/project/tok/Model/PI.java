package project.tok.Model;

public record PI() implements Token{
	@Override
	public String toString(){
		return "π";
	}
}
