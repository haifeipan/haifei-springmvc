package com.haifeiedu.haifeispringmvc.servlet;

import com.haifeiedu.haifeispringmvc.annotation.AutoWired;
import com.haifeiedu.haifeispringmvc.annotation.Controller;
import com.haifeiedu.haifeispringmvc.annotation.RequestMapping;
import com.haifeiedu.haifeispringmvc.annotation.RequetsParam;
import com.haifeiedu.haifeispringmvc.context.HaifeiWebApplicationContext;
import com.haifeiedu.haifeispringmvc.handler.HaifeiHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 1. "HaifeiDispatcherServlet" do the same work as original "DispatcherServlet"
 * 2. Essentially, "HaifeiDispatcherServlet" is a servlet, it extends from HttpServlet.
 * 3. reminder: here we need to use the Knowledge of servlet in Java Web
 */
public class HaifeiDispatcherServlet extends HttpServlet {

    private List<HaifeiHandler> handlerList = new ArrayList<>();
    HaifeiWebApplicationContext haifeiWebApplicationContext = null;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        // get the contextConfigLocation from web.xml

        /**
         *
         *     <init-param>
         *       <param-name>contextConfigLocation</param-name>
         *       <param-value>classpath:haifeispringmvc.xml</param-value>
         *     </init-param>
         *
         */
        String configLocation = servletConfig.getInitParameter("contextConfigLocation");

        haifeiWebApplicationContext = new HaifeiWebApplicationContext(configLocation);
        haifeiWebApplicationContext.init();
        initHandlerMapping();
        System.out.println("handlerList = " + handlerList);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("--HaifeiDispatcherServlet--doGet--");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("--HaifeiDispatcherServlet--doPost--");
        executeDispatch(req, resp);
    }

    /**
     * This method is used to store all the handler(url,controller,method) into List.
     */
    private void initHandlerMapping() {
        // check if the ioc container empty
        if (haifeiWebApplicationContext.ioc.isEmpty()) {
            return;
        }

        // iterate each bean object in the ioc container, and then finish url mapping, finally add to list
        for (Map.Entry<String, Object> entry : haifeiWebApplicationContext.ioc.entrySet()) {
            //get the class of the bean in IOC
            Class<?> clazz = entry.getValue().getClass();

            // if the bean is controller, we process it.

            if (clazz.isAnnotationPresent(Controller.class)) {
                // get all the method objects in the bean
                Method[] declaredMethods = clazz.getDeclaredMethods();
                // iterate each method
                for (Method declaredMethod : declaredMethods) {
                    if (declaredMethod.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMappingAnnotation = declaredMethod.getAnnotation(RequestMapping.class);
                        //    web project + url
                        //   /springmvc/monster/list
                        //   String url = getServletContext().getContextPath() + requestMappingAnnotation.value();

                        //   /monster/list
                        String url = requestMappingAnnotation.value();

                        // create HaifeiHandler we defined by ourselves, which is used to record the relationship of URL, controller, method
                        HaifeiHandler haifeiHandler = new HaifeiHandler(url, entry.getValue(), declaredMethod);
                        handlerList.add(haifeiHandler);
                    }
                }
            }

        }

    }

    /**
     * Return the HaifeiHandler according to the request
     *
     * @param request
     * @return
     */
    private HaifeiHandler getHaifeiHandler(HttpServletRequest request) {
        // First we get the URI of user request
        // For example: http://localhost:8080/springmvc/monster/list
        // URI = /springmvc/monster/list
        String requestURI = request.getRequestURI();

        // pay attention
        // URI is different from URL, the difference is the web project path/name
        // 2 solutions :
        // solution 1: in the configuration of Tomcat, we can configure application context into => /
        // solution 2: when we create the haifeiHandler, add the web project path/name => getServletContext().getContextPath()

        // iterate each Handler in HandlerList
        for (HaifeiHandler haifeiHandler : handlerList) {
            if (requestURI.equals(haifeiHandler.getUrl())) { // which means match successfully
                return haifeiHandler;
            }
        }

        return null;
    }

    /**
     * This method is used to finish the dispatch to different controllers.
     *
     * @param request
     * @param response
     */
    private void executeDispatch(HttpServletRequest request, HttpServletResponse response) {
        HaifeiHandler haifeiHandler = getHaifeiHandler(request);


        try {
            if (null == haifeiHandler) {
                response.getWriter().print("<h1>404 NOT FOUND </h1>");
            } else {
                Class<?>[] parameterTypes = haifeiHandler.getMethod().getParameterTypes();
                int n = parameterTypes.length;
                Object[] parameters = new Object[n];
                for (int i = 0; i < n; i++) {
                    Class<?> parameterType = parameterTypes[i];

                    if ("HttpServletRequest".equals(parameterType.getSimpleName())) {
                        parameters[i] = request;
                    } else if ("HttpServletResponse".equals(parameterType.getSimpleName())) {
                        parameters[i] = response;
                    }
                }

                Map<String, String[]> parameterMap = request.getParameterMap();
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    String name = entry.getKey();

                    // in order to simply the case,
                    // we only consider the situation where parameter is single, don't consider the data like checkbox
                    String value = entry.getValue()[0];
                    // we should know what index does the paramount belong to.
                    // find the index of matched parameters.
                    int index = getIndexRequestParameterIndex(haifeiHandler.getMethod(), name);
                    if (index != -1) {
                        parameters[index] = value;
                    } else {// if we cannot find @RequestParam correctly, we will consider use default way to handle it

                        // solution
                        // 1. get all the formal parameters in the target method (we will write a specific method)
                        // 2. iterate each formal parameter in the target method to check if it is matched.
                        List<String> formalParameters = getFormalParameters(haifeiHandler.getMethod());
                        for (int i = 0; i < formalParameters.size(); i++) {

                            // if matched, put it into parameters
                            if (formalParameters.get(i).equals(name)) {
                                parameters[i] = value;
                                break;
                            }
                        }
                    }


                }


                // haifeiHandler.getMethod().invoke(haifeiHandler.getController(), request, response);
                haifeiHandler.getMethod().invoke(haifeiHandler.getController(), parameters);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private List<String> getFormalParameters(Method method) {
        Parameter[] parameters = method.getParameters();
        List<String> formalParameters = new ArrayList<>();

        for (Parameter parameter : parameters) {
            formalParameters.add(parameter.getName());
        }

        return formalParameters;
    }

    /**
     * find the index of matched parameters.
     * @param method    target method
     * @param name  parameter name
     * @return
     */
    private int getIndexRequestParameterIndex(Method method, String name) {
        Parameter[] parameters = method.getParameters();
        int n = parameters.length;
        for (int i = 0; i < n; i++) {
            Parameter parameter = parameters[i];
            // check if this parameter has @RequestParam annotation
            boolean annotationPresent = parameter.isAnnotationPresent(RequetsParam.class);
            if (annotationPresent) { // means the parameter has @RequestParam annotation

                // get the value of this @RequetsParam(value = "xxx")
                RequetsParam requestParamAnnotation = parameter.getAnnotation(RequetsParam.class);
                String value = requestParamAnnotation.value();

                // check if matched
                if (name.equals(value)) {
                    // if matched, index i is the index of target parameter
                    return i;
                }
            }
        }
        // cannot match
        return -1;
    }


}
