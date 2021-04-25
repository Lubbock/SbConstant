package com.lame.sbconstant.detect.visit;

import examples.Java8Parser;
import examples.Java8ParserBaseVisitor;
import lombok.Getter;

public class MethodFieldExtract extends Java8ParserBaseVisitor<Void> {

    @Getter InvokeStatement invokeStatement = new InvokeStatement();

    @Override
    public Void visitMethodInvocation(Java8Parser.MethodInvocationContext ctx) {
        String text = ctx.getText();
        String[] filterMethod = new String[]{
                "log.info(", "log.error(", "Result.error",
                "setMessage","error500","error400",
                "Result.ok(", "System.out.println","errorMessage.add"
        };
        boolean ignore = false;
        for (String s : filterMethod) {
            if (text.contains(s)) {
                ignore = true;
                break;
            }
        }
        if (!ignore) {
            invokeStatement.visit(ctx);
        }
        return super.visitMethodInvocation(ctx);
    }

    @Override
    public Void visitLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx) {
        invokeStatement.visit(ctx);
        return super.visitLocalVariableDeclarationStatement(ctx);
    }
}
