package com.haifeiedu.controller;

import com.haifeiedu.entity.Monster;
import com.haifeiedu.haifeispringmvc.annotation.*;
import com.haifeiedu.service.MonsterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

    @RequestMapping(value = "/monster/find")
    public void findMonsterByName(HttpServletRequest request, HttpServletResponse response, /*@RequetsParam(value = "name") */ String name) {
        // set encoding and return type
        response.setContentType("text/html;charset=utf-8");
        StringBuilder content = new StringBuilder("<h1>The information of monster list</h1>");
//        List<Monster> monsters = monsterService.listMonster();
        List<Monster> monsters = monsterService.findMonsterByName(name);


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

    @RequestMapping(value = "/monster/login")
    public String login(HttpServletRequest request, HttpServletResponse response, String mName) {
        System.out.println("nName accepted: " + mName);
        boolean b = monsterService.login(mName);
        request.setAttribute("mName", mName);
        if (b) {
//             demo forward
//
//             return "forward:/login_ok.jsp";
//
//             demo redirect
//             return "redirect:/login_ok.jsp";

            return "/login_ok.jsp";
        } else {
            return "forward:/login_error.jsp";
        }
    }

    /**
     * @param request
     * @param response
     * @return
     *
     * 1. The result returned by this method is given to SpringMVC where the reflection invoke happens
     * 2. we accept the result and handle it in SpringMVC fundamental layer.
     */
    @RequestMapping(value = "/monster/list/json")
    @ResponseBody
    public List<Monster> findMonsterByJson(HttpServletRequest request, HttpServletResponse response) {
        List<Monster> monsters = monsterService.listMonster();
        return monsters;
    }
}
