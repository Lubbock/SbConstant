package com.lame.scanapi;



import com.lame.detect.strategy.APIDetectStrategy;
import com.lame.detect.vo.ApiClassMeta;
import com.lame.detect.vo.ClassMeta;
import com.lame.scanapi.vo.ControllerModule;
import com.lame.scanapi.vo.Module;
import com.lame.scanapi.vo.Project;
import core.analy.Java8Lexer;
import core.analy.Java8Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScanApiApp {

    public static Project scanProject(String baseScan) {
        List<Module> modules = new ArrayList<>();
        File f = new File(baseScan);
        Project project = new Project();
        project.setScanBasename(baseScan);
        project.setModules(modules);
        for (File file : f.listFiles()) {
            if (file.isDirectory()) {
                Module module = new Module();
                module.name(file.getName());
                List<ControllerModule> controllerModules = new ArrayList<>();
                File conDir = new File(file.getAbsolutePath(), "controller");
                if (conDir.exists()) {
                    modules.add(module);
                    for (File tf : conDir.listFiles()) {
                        if (tf.isFile() && tf.getName().endsWith("Controller.java")) {
                            ControllerModule controllerModule = new ControllerModule();
                            controllerModule.setName(controllerModule.getName());
                            controllerModule.setFile(tf);
                            controllerModules.add(controllerModule);
                        }
                    }
                    module.children(controllerModules);
                }
            }
        }
        return project;
    }

    public static void moduleScan(ControllerModule cm) {
        String f = cm.getFile().getAbsolutePath();
        try {
            Lexer lexer = new Java8Lexer(CharStreams.fromFileName(f));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Java8Parser parser = new Java8Parser(tokens);
            Java8Parser.CompilationUnitContext tree = parser.compilationUnit();
            APIDetectStrategy detect = new APIDetectStrategy();
            ApiClassMeta apiClassMeta = (ApiClassMeta)detect.detect(tree);
            cm.setApiClassMeta(apiClassMeta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String baseScan = "/media/lame/0DD80F300DD80F30/koal/kcsp-admin/kcsp-boot/kcsp-boot-module-system/src/main/java/kl/kcsp/modules";
        Project project = scanProject(baseScan);
        List<Module> modules = project.getModules();
        for (Module module : modules) {
            for (ControllerModule cm : module.children()) {
                moduleScan(cm);
            }
        }
        for (Module module : modules) {
            System.out.println("********************************");
            System.out.println(module.name());
            List<ControllerModule> children = module.children();
            for (ControllerModule child : children) {
                System.out.println("==============================api");
                ApiClassMeta apiClassMeta = child.getApiClassMeta();
                System.out.println("baseApipath " + apiClassMeta.getBaseApiPath());
                apiClassMeta.getApis().stream().forEach(System.out::println);
            }
            System.out.println("**********************end****");
        }
    }
}
