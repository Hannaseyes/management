package com.management.rd.controller;

import java.awt.image.BufferedImage;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;
import com.management.rd.constants.KaptchaConsts;
import com.management.rd.utils.IOUtil;

/**
 * 验证码控制器，包括网站验证码、手机验证码的生成及校验
 * @author Wang Hanqing
 *
 */
@Controller  
@RequestMapping("/captcha") 
public class CaptchaController {
    @Resource 
    private Producer captchaProducer;    
    
    /**
     * 生成用户登录时的验证码
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/getLoginCaptcha")  
    public ModelAndView getLoginCaptcha(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{  
        /* Expires过时期限值，指浏览器或缓存服务器在该时间点后必须从真正的服务器中获取新的页面信息 */  
        response.setDateHeader("Expires", 0);  
        /* 浏览器和缓存服务器都不应该缓存页面信息 */  
//      response.setHeader("Cache-Control", "no-cache");  
        /* 请求和响应的信息都不应该被存储在对方的磁盘系统中 */  
//      response.setHeader("Cache-Control", "no-store");  
        /* 浏览器和缓存服务器都可以缓存页面信息 */  
//      response.setHeader("Cache-Control", "public");  
        /* 对于客户机的每次请求，代理服务器必须向服务器验证缓存是否过时 */  
//      response.setHeader("Cache-Control", "must-revalidate");  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        /* 不让浏览器或中间缓存服务器缓存页面，配合Expires 置为0限定更保险 */  
        response.setHeader("Pragma", "no-cache");  
        /* 
         * response.setContentType(MIME)的作用是使客户端浏览器区分不同类型的数据， 
         * 并根据不同的MIME调用浏览器内部不同的程序嵌入模块来处理相应的数据 
         */  
        response.setContentType("image/jpeg");  
        /* 生成验证码 */  
        String capText = captchaProducer.createText();  
        /* 保存验证码到Session中 */  
        request.getSession().setAttribute(KaptchaConsts.LOGIN_KAPTCHA_SESSION_KEY, capText);  
        /* 使用给定文字创建图片 */  
        BufferedImage bi = captchaProducer.createImage(capText);  
        /* 数据写入输出流 */
        IOUtil.imageWrite(bi, "jpg", response);
        
        return null;
    }  
}
