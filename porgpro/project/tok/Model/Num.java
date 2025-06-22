package project.tok.Model;

public record Num(double value) implements Token{
	public double getValue(){
		return this.value();
	}

	@Override
	public String toString(){
		return ""+this.value()+"";
	}
}
