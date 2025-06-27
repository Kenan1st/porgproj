package project.ast;

public sealed interface Expr permits UExpr,BOp,BinOp,Func,Va,Cnst,UOp,E,P{}
