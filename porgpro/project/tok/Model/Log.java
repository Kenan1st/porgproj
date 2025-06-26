package project.tok.Model;

public record Log() implements Token{
	@Override
	public String toString(){
		return "log";
	}
}
