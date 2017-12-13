package com.management.rd.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.management.rd.pojo.SystemUser;
import com.management.rd.pojo.User;
import com.management.rd.service.IUserService;
import com.management.rd.utils.CheckUtil;
import com.management.rd.utils.Constants;
import com.management.rd.utils.MD5Util;
import com.management.rd.utils.RequestUtil;

@Controller  
@RequestMapping("/user") 
public class UserController {
    @Resource  
    private IUserService userService;  
    
    @RequestMapping("/login")  
    public String login(HttpServletRequest request, HttpSession session, Model model){
        // 获取请求参数
        Map<String, String> reqMap = RequestUtil.decryptParamters(request);
        String username = reqMap.get("username");
        String password = reqMap.get("password");
        String kaptcha = reqMap.get("kaptcha");
        
        SystemUser sysUser = new SystemUser();
        sysUser.setUsername(username);
        sysUser.setPassword(MD5Util.getMD5(password));
        String sessionKaptcha = String.valueOf(session.getAttribute(Constants.LOGIN_KAPTCHA_SESSION_KEY));

        /**
         * 验证码进行拦截
         */
        if (CheckUtil.emptyCheck(kaptcha)) {
            return "login";
        }
        if (!CheckUtil.noSensitiveCheck(kaptcha, sessionKaptcha)) {
            return "login";
        }
        
        return "index";
    }
    
    @RequestMapping("/userList")  
    public String toIndex(HttpServletRequest request,Model model){  
        List<User> user = this.userService.listUser();  
        model.addAttribute("user", user);  
        return "index";  
    }
    
}
