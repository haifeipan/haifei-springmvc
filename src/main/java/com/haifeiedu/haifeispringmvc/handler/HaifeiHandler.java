package com.haifeiedu.haifeispringmvc.handler;

import java.lang.reflect.Method;

/**
 * This class is used to record the relationship in url, controller, method
 */
public class HaifeiHandler {
    private String url;
    private Object controller;
    private Method method;

    public HaifeiHandler(String url, Object controller, Method method) {
        this.url = url;
        this.controller = controller;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "HaifeiHandler{" +
                "url='" + url + '\'' +
                ", controller=" + controller +
                ", method=" + method +
                '}';
    }
}
