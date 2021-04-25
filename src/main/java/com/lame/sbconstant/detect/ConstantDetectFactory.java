package com.lame.sbconstant.detect;

import com.lame.sbconstant.detect.strategy.CommonDetectStrategy;
import com.lame.sbconstant.detect.strategy.EntityDetectStrategy;
import com.lame.sbconstant.detect.vo.FileType;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * 常量工厂
 **/
public class ConstantDetectFactory {

    public static DetectContext getDetectContext(FileType fileType) {
        DetectContext detectContext = null;
        switch (fileType) {
            case ENTITY:
                detectContext = new DetectContext(new EntityDetectStrategy());
                break;
            case DAO:
            case SERVICE:
            case COMMON:
            default:
                detectContext = new DetectContext(new CommonDetectStrategy());
                break;
        }
        return detectContext;
    }
}
