package com.haifeiedu.test;

import com.haifeiedu.haifeispringmvc.context.HaifeiWebApplicationContext;
import com.haifeiedu.haifeispringmvc.xml.XMLParser;
import org.junit.Test;

public class HaifeiSpringMVCTest {

    @Test
    public void readXML() {
        String basePackage = XMLParser.getBasePackage("haifeispringmvc.xml");
        System.out.println("basePackage=" + basePackage);
    }
    @Test
    public void generalTest() {
        HaifeiWebApplicationContext haifeiWebApplicationContext = new HaifeiWebApplicationContext();
        haifeiWebApplicationContext.scanPackage("com.haifeiedu.controller");
    }
}
