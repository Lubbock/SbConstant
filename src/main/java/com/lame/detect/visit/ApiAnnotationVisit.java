package com.lame.detect.visit;

import com.lame.sbconstant.utils.Antlr4Utils;
import core.analy.Java8Parser;
import core.analy.Java8ParserBaseVisitor;

public class ApiAnnotationVisit  extends Java8ParserBaseVisitor<Void> {



    @Override
    public Void visitAnnotation(Java8Parser.AnnotationContext ctx) {
        System.out.println(Antlr4Utils.getFullText(ctx));
        return super.visitAnnotation(ctx);
    }
}
