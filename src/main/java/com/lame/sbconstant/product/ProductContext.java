package com.lame.sbconstant.product;

import com.lame.sbconstant.detect.vo.ClassMeta;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.antlr.v4.runtime.tree.ParseTree;

@Data
@AllArgsConstructor
public class ProductContext {
    private ProductStrategy productStrategy;

    public void product(ParseTree parseTree, ClassMeta classMeta, String fp) {
        productStrategy.product(parseTree, classMeta, fp);
    }
}
