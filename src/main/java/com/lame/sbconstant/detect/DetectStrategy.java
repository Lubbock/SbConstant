package com.lame.sbconstant.detect;

import com.lame.sbconstant.detect.vo.ClassMeta;
import org.antlr.v4.runtime.tree.ParseTree;

public interface DetectStrategy {
    ClassMeta detect(ParseTree parseTree);
}
