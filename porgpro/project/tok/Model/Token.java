package project.tok.Model;

public sealed interface Token permits Num,Op,Sp,Tf,Ident,Sqrt,Sin,Tan,Cos,Pow,Log,Pi,Eul,Space {
	
@Override String toString();
}
