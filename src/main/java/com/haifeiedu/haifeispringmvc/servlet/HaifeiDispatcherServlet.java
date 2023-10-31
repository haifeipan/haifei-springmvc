package com.haifeiedu.haifeispringmvc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. "HaifeiDispatcherServlet" do the same work as original "DispatcherServlet"
 * 2. Essentially, "HaifeiDispatcherServlet" is a servlet, it extends from HttpServlet.
 * 3. reminder: here we need to use the Knowledge of servlet in Java Web
 */
public class HaifeiDispatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--HaifeiDispatcherServlet--doGet--");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--HaifeiDispatcherServlet--doPost--");
    }
}
