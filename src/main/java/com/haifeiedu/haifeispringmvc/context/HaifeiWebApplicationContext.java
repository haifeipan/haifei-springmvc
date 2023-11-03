package com.haifeiedu.haifeispringmvc.context;

import com.haifeiedu.haifeispringmvc.annotation.Controller;
import com.haifeiedu.haifeispringmvc.xml.XMLParser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class HaifeiWebApplicationContext {
    // define the filed: classFullPathList, store the full path of classes in the scan package or subpackage
    private List<String> classFullPathList = new ArrayList<>();

    // define field ioc, used to store bean objects generated by reflection.
    public ConcurrentHashMap<String, Object> ioc = new ConcurrentHashMap<>();
    // pay attention Object V.S Objects

    /**
     * This method is used to finish the initiation of spring container.
     */
    public void init() {
        String basePackage = XMLParser.getBasePackage("haifeispringmvc.xml");
//        scanPackage(basePackage);
        String[] basePackages = basePackage.split(",");
        if (basePackages.length > 0) {
            for (String pack : basePackages) {
                scanPackage(pack);
            }
        }
        System.out.println("classFullPathList = " + classFullPathList);
        executeInstance();
        System.out.println("ioc container = " + ioc);
    }

    /**
     *
     * @param pack the package we need to scan, for example "com.haifeiedu.controller"
     */
    public void scanPackage(String pack) {
        // get the absolute path of the package
        // for example:
        // the absolute path of "com.haifeiedu.controller" is
        // "/C:/Users/harvey/Desktop/Job/Training/HSPJava/JavaEngineerLearningResource/SSM/SpringMVC/code/phfedu_springmvc/haifei-springmvc/target/haifei-springmvc/WEB-INF/classes/com/haifeiedu/controller/"
        // Pay attention:
        // 1: don't use Junit to test, otherwise the url will be null
        // 2： use Tomcat to test the url

        URL url = this.getClass().getClassLoader().getResource("/" + pack.replaceAll("\\.", "/"));
//        System.out.println("url = " + url);
        String path = url.getFile();
        System.out.println("path= " + path);
        File dir = new File(path);

        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                scanPackage(pack + "." + f.getName());
            } else {
                // put all the file path into classFullPathList
                // At this time, we don't need to consider if this file is ending with ".class"
                // and we also don't need to consider if this class file need to be injected into container (@Controller)
                // we will handle above two later.
                // At this time, we all put all the file path into the classFullPathList
                String classFullPath = pack + "." + f.getName().replaceAll(".class", "");
                classFullPathList.add(classFullPath);
            }
        }



    }

    /**
     * using reflection to instantiate class scanned before and store them into container
     */
    public void executeInstance() {
        if (classFullPathList.size() == 0) {
            return;
        }


        try {
            for (String classFullPath : classFullPathList) {
                Class<?> clazz = Class.forName(classFullPath);
                if(clazz.isAnnotationPresent(Controller.class)) {
                    String beanName = clazz.getSimpleName().substring(0, 1).toLowerCase() +
                            clazz.getSimpleName().substring(1);
                    ioc.put(beanName, clazz.newInstance());
                }

                // if we have more annotations, we can add more code. But here we just code a demo.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
