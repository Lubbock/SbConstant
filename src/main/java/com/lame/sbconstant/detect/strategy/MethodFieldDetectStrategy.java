package com.lame.sbconstant.detect.strategy;

import com.lame.sbconstant.detect.DetectStrategy;
import com.lame.sbconstant.detect.visit.MethodFieldExtract;
import com.lame.sbconstant.detect.vo.ClassMeta;
import com.lame.sbconstant.detect.vo.LineExtraMeta;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

public class MethodFieldDetectStrategy implements DetectStrategy {
    @Override
    public ClassMeta detect(ParseTree parseTree) {
        MethodFieldExtract extract = new MethodFieldExtract();
        extract.visit(parseTree);
        List<LineExtraMeta> lineExtraMetas = extract.getFieldStatementVisit().getKv();
        List<LineExtraMeta> metas = new ArrayList<>();
        Set<String> uniqueMeta = new HashSet<>();
        //去掉重复定义
        for (LineExtraMeta lineExtraMeta : lineExtraMetas) {
            String variableName = lineExtraMeta.getVariableName();
            if (uniqueMeta.contains(variableName)) {
                continue;
            }else {
                uniqueMeta.add(variableName);
                metas.add(lineExtraMeta);
            }
        }
        ClassMeta classMeta = new ClassMeta();
        classMeta.setLineExtraMetas(metas);
        return classMeta;
    }
}
