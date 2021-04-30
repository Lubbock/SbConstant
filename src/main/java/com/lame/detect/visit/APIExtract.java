package com.lame.detect.visit;

import com.google.common.collect.Lists;
import com.lame.detect.vo.ApiClassMeta;
import com.lame.sbconstant.utils.Antlr4Utils;
import core.analy.Java8Parser;
import core.analy.Java8ParserBaseVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class APIExtract extends Java8ParserBaseVisitor<String> {

    ApiClassMeta apiClassMeta = new ApiClassMeta();

    @Override
    public String visitSingleElementAnnotation(Java8Parser.SingleElementAnnotationContext ctx) {
        Java8Parser.TypeNameContext typeNameContext = ctx.typeName();
        if (typeNameContext != null) {
            String text = ctx.typeName().getText();
            if (text.equals("RequestMapping")) {
                return ctx.elementValue().getText();
            }
        }
        return super.visitSingleElementAnnotation(ctx);
    }


    @Override
    public String visitClassModifier(Java8Parser.ClassModifierContext ctx) {
        Java8Parser.AnnotationContext annotation = ctx.annotation();
        if (annotation != null) {
            Java8Parser.SingleElementAnnotationContext singleElementAnnotationContext = annotation.singleElementAnnotation();
            if (singleElementAnnotationContext != null) {
                String s = visitSingleElementAnnotation(singleElementAnnotationContext);
                if (StringUtils.isNotBlank(s)) {
                    apiClassMeta.setBaseApiPath(s.substring(1, s.length()-1));
                }
            }
        }
        return super.visitClassModifier(ctx);
    }

    @Override
    public String visitNormalAnnotation(Java8Parser.NormalAnnotationContext ctx) {
        Java8Parser.TypeNameContext typeNameContext = ctx.typeName();
        ArrayList<String> accept = Lists.newArrayList("GetMapping", "PostMapping", "DeleteMapping", "PutMapping","RequestMapping");
        if (typeNameContext != null) {
            String fullText = Antlr4Utils.getFullText(typeNameContext);
            if (StringUtils.equalsAny(fullText, accept.toArray(new String[0]))) {
                Java8Parser.ElementValuePairListContext elementValuePairListContext = ctx.elementValuePairList();
                if (elementValuePairListContext != null) {
                    Java8Parser.ElementValuePairContext elementValuePairContext = elementValuePairListContext.elementValuePair(0);
                    if (elementValuePairContext != null) {
                        TerminalNode identifier = elementValuePairContext.Identifier();
                        if (identifier != null) {
                            if (
                                    identifier.getText().equals("value")
                                            || identifier.getText().equals("path")
                            ) {
                                String url = elementValuePairContext.elementValue().getText();
                                apiClassMeta.addApi(url.substring(1, url.length() - 1));
                            };
                        }
                    }
                }
            }
        }
        return super.visitNormalAnnotation(ctx);
    }

    @Override
    public String visitMethodModifier(Java8Parser.MethodModifierContext ctx) {
        return super.visitMethodModifier(ctx);
    }

    public ApiClassMeta getApiClassMeta() {
        return apiClassMeta;
    }

    public void setApiClassMeta(ApiClassMeta apiClassMeta) {
        this.apiClassMeta = apiClassMeta;
    }
}
