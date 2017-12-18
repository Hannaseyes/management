package com.management.rd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.management.rd.utils.QRCodeUtil;

@Controller
@RequestMapping("/qrCode")
public class QRCodeController {

    @RequestMapping("/qrCode")
    public ModelAndView qeCode(HttpServletRequest request, HttpServletResponse response) {
        String requestURL = "https://www.cheyr.cn/";
        // 依次为内容(不支持中文),宽,长,中间图标路径,储存路径
        QRCodeUtil.bgQRCode(requestURL, 300, 300, 50, 50, "D://bg.jpg", response);
        return null;
    }
}
