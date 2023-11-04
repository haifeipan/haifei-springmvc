package com.haifeiedu.controller;

import com.haifeiedu.entity.Monster;
import com.haifeiedu.haifeispringmvc.annotation.AutoWired;
import com.haifeiedu.haifeispringmvc.annotation.Controller;
import com.haifeiedu.haifeispringmvc.annotation.RequestMapping;
import com.haifeiedu.service.MonsterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class MonsterController {

    @AutoWired
    private MonsterService monsterService;

    /**
     * SpringMVC supports original servlet API.
     * In order to know hot it work fundamentally, we design two parameters here:HttpServletRequest request, HttpServletResponse response
     */
    @RequestMapping(value = "/monster/list")
    public void listMonster(HttpServletRequest request, HttpServletResponse response) {
        // set encoding and return type
        response.setContentType("text/html;charset=utf-8");
        StringBuilder content = new StringBuilder("<h1>The information of monster list</h1>");
        List<Monster> monsters = monsterService.listMonster();
        content.append("<table border='1px' width='500px' style='border-collapse:collapse'>");
        for (Monster monster : monsters) {
            content.append("<tr><td>" + monster.getId() +
                    "</td><td>" + monster.getName() +
                    "</td><td>" + monster.getSkill() +
                    "</td><td>" + monster.getAge() +
                    "</td></tr>");
        }
        content.append("</table>");


        // return information of writer
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(content.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
