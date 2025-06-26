package project.ast;

public record UExpr(UOp op, Expr e) implements Expr {}
