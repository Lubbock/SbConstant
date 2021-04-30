package com.lame.detect.strategy;

import com.lame.detect.DetectStrategy;
import com.lame.detect.visit.APIExtract;
import com.lame.detect.visit.EntityFieldExtract;
import com.lame.detect.vo.ApiClassMeta;
import com.lame.detect.vo.ClassMeta;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * 控制器扫描
 * **/
public class APIDetectStrategy implements DetectStrategy {

    @Override
    public ClassMeta detect(ParseTree parseTree) {
        APIExtract extract = new APIExtract();
        extract.visit(parseTree);
        ApiClassMeta apiClassMeta = extract.getApiClassMeta();
        return apiClassMeta;
    }
}
