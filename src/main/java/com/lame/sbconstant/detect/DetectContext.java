package com.lame.sbconstant.detect;

import com.lame.sbconstant.detect.vo.ClassMeta;
import com.lame.sbconstant.detect.vo.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

@Data
@AllArgsConstructor
public class DetectContext {

    private DetectStrategy detectStrategy;

    public ClassMeta detect(ParseTree parseTree, String fp) {
        ClassMeta detect = detectStrategy.detect(parseTree);
        File f = new File(fp);
        detect.setName(StringUtils.substringBeforeLast(f.getName(), "."));
        return detect;
    }
}
