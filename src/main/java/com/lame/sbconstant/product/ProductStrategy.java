package com.lame.sbconstant.product;

import com.lame.sbconstant.detect.vo.ClassMeta;
import org.antlr.v4.runtime.tree.ParseTree;

public interface ProductStrategy {
    void product(ParseTree parseTree, ClassMeta classMeta, String fp);
}
