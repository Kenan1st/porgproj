package project.tok.Model;

public record Ident(String name) implements Token{
	@Override
	public String toString(){
		return this.name();
	}
}
