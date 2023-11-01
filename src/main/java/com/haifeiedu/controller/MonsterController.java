package com.haifeiedu.controller;
import com.haifeiedu.haifeispringmvc.annotation.Controller;
import com.haifeiedu.haifeispringmvc.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class MonsterController {
    /**
     *
     *
     * SpringMVC supports original servlet API.
     * In order to know hot it work fundamentally, we design two parameters here:HttpServletRequest request, HttpServletResponse response
     */
    @RequestMapping(value = "/list/monster")
    public void listMonster(HttpServletRequest request, HttpServletResponse response) {
        // set encoding and return type
        response.setContentType("text/html;charset=utf-8");

        // return information of writer
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write("<h1> The list of monsters </h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
