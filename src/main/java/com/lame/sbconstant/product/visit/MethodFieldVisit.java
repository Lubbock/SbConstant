package com.lame.sbconstant.product.visit;

import com.lame.sbconstant.detect.visit.InvokeStatement;
import com.lame.sbconstant.detect.vo.LineExtraMeta;
import examples.Java8Parser;
import examples.Java8ParserBaseVisitor;
import lombok.Getter;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

import java.util.List;

public class MethodFieldVisit extends Java8ParserBaseVisitor<Void> {
    @Getter
    InvokeStatement invokeStatement;
    @Getter
    TokenStreamRewriter rewriter;

    List<LineExtraMeta> lineExtraMetas;

    public MethodFieldVisit(TokenStream tokens, List<LineExtraMeta> lineExtraMetas) {
        this.rewriter  =new TokenStreamRewriter(tokens);
        this.invokeStatement = new InvokeStatement(rewriter);
        this.lineExtraMetas = lineExtraMetas;
    }

    public String fuck(String key, String val) {
        return String.format("\n\tpublic static final %s = %s;", key, val);
    }
    @Override
    public Void visitClassBody(Java8Parser.ClassBodyContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (LineExtraMeta lineExtraMeta : lineExtraMetas) {
            sb.append(fuck(lineExtraMeta.getVariableName(), lineExtraMeta.getValue()));
        }
        rewriter.insertAfter(ctx.start, sb.toString());
        return super.visitClassBody(ctx);
    }

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
