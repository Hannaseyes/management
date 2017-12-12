package com.management.rd.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.management.rd.pojo.User;
import com.management.rd.service.IUserService;

@Controller  
@RequestMapping("/user") 
public class UserController {
    @Resource  
    private IUserService userService;  
      
    @RequestMapping("/userList")  
    public String toIndex(HttpServletRequest request,Model model){  
        List<User> user = this.userService.listUser();  
        model.addAttribute("user", user);  
        return "userList";  
    }  
}
