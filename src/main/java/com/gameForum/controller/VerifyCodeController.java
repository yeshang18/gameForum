package com.gameForum.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/verify")
@Api(tags = "验证码获取接口")
public class VerifyCodeController {

    @GetMapping("/get")
    @ApiOperation("获取验证码")
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(150,40,5,4);
        response.setContentType("image/png");
        response.setHeader("Pragma","No-cache");
        captcha.write(response.getOutputStream());
        String verifyCode = captcha.getCode();
        session.setAttribute("verifyCode",verifyCode);
    }
}
