package com.lame.scanapi;



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
                modules.add(module);
                System.out.println("=====================\nmodule-"+module.name());
                List<ControllerModule> controllerModules = new ArrayList<>();
                File conDir = new File(file.getAbsolutePath(), "controller");
                if (conDir.exists()) {
                    for (File tf : conDir.listFiles()) {
                        if (tf.isFile() && tf.getName().endsWith("Controller.java")) {
                            ControllerModule controllerModule = new ControllerModule();
                            controllerModule.setName(controllerModule.getName());
                            controllerModule.setFile(tf);
                            controllerModules.add(controllerModule);
                            System.out.println(tf.getName());
                        }
                    }
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
    }
}
