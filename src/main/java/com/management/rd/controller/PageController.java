package com.management.rd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转控制器，用以整合所有直接的跳转请求
 * @author Wang Hanqing
 *
 */
@Controller  
@RequestMapping("/page") 
public class PageController {

    /**
     * 登录页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")  
    public String toIndex(HttpServletRequest request,HttpServletResponse response){   
        return "login";  
    } 
}
