package com.xiajl.google_authenticator_web_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @program: google_authenticator_web_demo
 * @description:
 * @author: francis
 * @create: 2019-04-06 17:03
 **/
@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final Map<String, String> userCodeMap = new ConcurrentHashMap<>();

    @GetMapping("/user/register")
    public String register(Model model, String user) {
        if(userCodeMap.containsKey(user)) {
            model.addAttribute("qrcode", GoogleAuthenticator.getQRBarcode(user, userCodeMap.get(user)));
        } else {
            // 该变量由注册用户时产生,并录入数据库，与用户关联
            String secret = GoogleAuthenticator.generateSecretKey();
            // 把这个qrcode生成二维码，用google身份验证器扫描二维码就能添加成功
            String qrcode = GoogleAuthenticator.getQRBarcode(user, secret);
            model.addAttribute("qrcode", qrcode);
            userCodeMap.put(user, secret);
        }
        return "user/register";
    }

    @GetMapping("/user/validCode")
    @ResponseBody
    public String validCode(String user, long code) {
        GoogleAuthenticator ga = new GoogleAuthenticator();
        ga.setWindowSize(5);
        if(!userCodeMap.containsKey(user)) {
            return "user not register";
        }

        boolean r = ga.checkCode(userCodeMap.get(user), code, System.currentTimeMillis());
        return "valid result is r:" + r;
    }

    // http://127.0.0.1:8080/user/register?user=francis.xjl@qq.com
    // http://127.0.0.1:8080/user/validCode?user=francis.xjl@qq.com&code=086175

}
