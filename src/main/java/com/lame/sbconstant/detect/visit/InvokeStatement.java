package com.lame.sbconstant.detect.visit;

import com.lame.sbconstant.detect.vo.LineExtraMeta;
import examples.Java8Parser;
import examples.Java8ParserBaseVisitor;
import lombok.Getter;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvokeStatement extends Java8ParserBaseVisitor<Void> {
    @Getter
    List<LineExtraMeta> kv = new ArrayList<>();
    TokenStreamRewriter rewriter;

    public InvokeStatement() {
    }

    public InvokeStatement(TokenStreamRewriter rewriter) {
        this.rewriter = rewriter;
    }

    public static boolean isIncludeZhCn(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");

        Matcher m = p.matcher(str);

        if (m.find()) {
            return true;
        }
        return false;
    }

    @Override
    public Void visitLiteral(Java8Parser.LiteralContext ctx) {
        TerminalNode stringLiteral = ctx.StringLiteral();
        TerminalNode integerLiteral = ctx.IntegerLiteral();
        TerminalNode booleanLiteral = ctx.BooleanLiteral();
        TerminalNode characterLiteral = ctx.CharacterLiteral();
        TerminalNode floatingPointLiteral = ctx.FloatingPointLiteral();
        LineExtraMeta lineExtraMeta = null;
        String name = "";
        if (stringLiteral != null) {
            String text = ctx.StringLiteral().getText();
            if (isIncludeZhCn(text)) {
                return null;
            }
            if (text.isEmpty()) {
                return null;
            }
            if (text.contains("-")) {
                return null;
            }
            if (text.contains(",")) {
                return null;
            }
            String s = text.toUpperCase(Locale.ROOT).toUpperCase(Locale.ROOT);
            name = "STR_" + s.substring(1, s.length() - 1);
            lineExtraMeta = new LineExtraMeta("String " + name, text);
            kv.add(lineExtraMeta);
        } else if (integerLiteral != null) {
            String text = ctx.IntegerLiteral().getText();
            String s = text.toUpperCase(Locale.ROOT).toUpperCase(Locale.ROOT);
            name = "INT_" + s;
            lineExtraMeta = new LineExtraMeta("Integer " + name, text);
            kv.add(lineExtraMeta);
        } else if (booleanLiteral != null) {
            String text = ctx.BooleanLiteral().getText();
            String s = text.toUpperCase(Locale.ROOT).toUpperCase(Locale.ROOT);
            name = "BOOL_" + s;
            lineExtraMeta = new LineExtraMeta("Boolean " + name, text);
            kv.add(lineExtraMeta);
        } else if (characterLiteral != null) {
            String text = ctx.CharacterLiteral().getText();
            String s = text.toUpperCase(Locale.ROOT).toUpperCase(Locale.ROOT);
            name = "CHAR_" + s.substring(1, s.length() - 1);
            lineExtraMeta = new LineExtraMeta("String " + name, text);
            kv.add(lineExtraMeta);
        } else if (floatingPointLiteral != null) {
            String text = ctx.FloatingPointLiteral().getText();
            String s = text.toUpperCase(Locale.ROOT).toUpperCase(Locale.ROOT);
            name = "FLOAT_" + s;
            lineExtraMeta = new LineExtraMeta("float " + name, text);
            kv.add(lineExtraMeta);
        }
        if (lineExtraMeta != null && rewriter != null) {
            rewriter.replace(ctx.start, name);
        }
        return null;
    }
}
