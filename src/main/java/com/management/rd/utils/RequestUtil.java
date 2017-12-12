package com.management.rd.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * 请求工具类
 * 
 * @author Wang Hanqing
 * 
 */
public class RequestUtil {
    private static final Log logger = LogFactory.getLog(RequestUtil.class);

    /**
     * 解析请求参数
     * 
     * @param request
     * @return
     */
    public static Map<String, String> decryptParamters(HttpServletRequest request) {
        Map<String, String> rtnMap = new HashMap<String, String>();

        try {
            // 获取get参数
            String reqString = request.getQueryString();

            if (StringUtils.isEmpty(reqString)) {
                // 解密 Base64 + 3DES
                String paramters = DES3Util.decode(reqString);
                // 特殊字符、中文汉字转码
                paramters = java.net.URLDecoder.decode(paramters, "UTF-8");
                String[] tmpArr = paramters.split("&");
                for (int i = 0; i < tmpArr.length; i++) {
                    String[] paramArr = tmpArr[i].split("=");
                    if (paramArr.length == 1) { // 参数值为空
                        rtnMap.put(paramArr[0], "");
                    } else if (paramArr.length == 2) {
                        rtnMap.put(paramArr[0], paramArr[1]);
                    } else {

                    }
                }
            }
        } catch (Exception e) {
            logger.debug("请求参数解析异常：" + e.getMessage());
        }

        return rtnMap;
    }
}
