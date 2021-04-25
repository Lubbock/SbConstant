package com.lame.sbconstant;


import com.lame.sbconstant.detect.AIDetect;
import com.lame.sbconstant.detect.ConstantDetectFactory;
import com.lame.sbconstant.detect.DetectContext;
import com.lame.sbconstant.detect.vo.ClassMeta;
import com.lame.sbconstant.detect.vo.FileType;
import com.lame.sbconstant.product.ProductContext;
import com.lame.sbconstant.product.ProductFactory;
import examples.Java8Lexer;
import examples.Java8Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;

public class SBConstant {
    static final String banner = "\033[36;4m" + " #####  ######   #####                                                 \n" +
            "#     # #     # #     #  ####  #    #  ####  #####   ##   #    # ##### \n" +
            "#       #     # #       #    # ##   # #        #    #  #  ##   #   #   \n" +
            " #####  ######  #       #    # # #  #  ####    #   #    # # #  #   #   \n" +
            "      # #     # #       #    # #  # #      #   #   ###### #  # #   #   \n" +
            "#     # #     # #     # #    # #   ## #    #   #   #    # #   ##   #   \n" +
            " #####  ######   #####   ####  #    #  ####    #   #    # #    #   #   \n" + "\033[0m";

    public static boolean aiDetect;
    public static void main(String[] args) {
        System.out.println(banner);
        String fp = "";
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-ai":
                        aiDetect = true;
                        break;
                    case "-f":
                        i++;
                        fp = args[i];
                        break;
                    default:
                        System.out.println("参数不正确");
                        return;
                }
            }
        }
        try {
            Lexer lexer = new Java8Lexer(CharStreams.fromFileName(fp));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Java8Parser parser = new Java8Parser(tokens);
            Java8Parser.CompilationUnitContext tree = parser.compilationUnit();
            FileType fileType = FileType.COMMON;
            if (aiDetect) {
                AIDetect aiDetect = new AIDetect();
                fileType = aiDetect.visit(tree);
            }
            DetectContext detectContext = ConstantDetectFactory.getDetectContext(fileType);
            ClassMeta detectMeta = detectContext.detect(tree, fp);
            ProductContext productContext = ProductFactory.getProductContext(fileType);
            productContext.product(tree, detectMeta, fp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
