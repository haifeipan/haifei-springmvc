package com.haifeiedu.controller;

import com.haifeiedu.haifeispringmvc.annotation.Controller;
import com.haifeiedu.haifeispringmvc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class OrderController {
    @RequestMapping(value = "/order/list")
    public void listOrder(HttpServletRequest request, HttpServletResponse response) {
        // set encoding and return type
        response.setContentType("text/html;charset=utf-8");

        // return information of writer
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write("<h1> The list of orders </h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/order/add")
    public void addOrder(HttpServletRequest request, HttpServletResponse response) {
        // set encoding and return type
        response.setContentType("text/html;charset=utf-8");

        // return information of writer
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write("<h1>Add orders</h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
