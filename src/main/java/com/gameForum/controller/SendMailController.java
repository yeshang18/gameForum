package com.gameForum.controller;

import com.gameForum.common.R;
import com.gameForum.entity.MailRequest;
import com.gameForum.service.SendMailService;
import com.gameForum.utils.CheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@RestController
@RequestMapping("/sendMail")
@Api(tags = "发送邮件接口")
public class SendMailController {


    @Autowired
    private SendMailService sendMailService;

    @PostMapping("/send")
    @ApiOperation("发送普通邮件")
    public R<String> sendMail(@RequestBody MailRequest mailRequest,HttpServletRequest request){
        if(mailRequest.getSendTo().isEmpty()){
            return R.error("邮箱为空，发送失败");
        }
        if(!CheckUtil.mailCheck(mailRequest.getSendTo())){
            return R.error("邮箱格式错误！");
        }

        mailRequest.setSubject("验证码");
        Random random = new Random();
        StringBuilder code= new StringBuilder();
        for(int i=0;i<6;i++) {
            int a = random.nextInt(10);
            code.append(a);
        }
        mailRequest.setText("您的验证码为"+code+",请于10分钟内输入验证，逾期验证码将失效！");
        sendMailService.sendSimpleMail(mailRequest);
        HttpSession session = request.getSession();
        session.setAttribute("code",code);
        session.setMaxInactiveInterval(600);
        return R.success("发送成功");
    }

    @PostMapping("/sendHtml")
    @ApiOperation("发送html邮件")
    public R<String> sendHtmlMail(@RequestBody MailRequest mailRequest, HttpSession session){
        if(mailRequest.getSendTo().isEmpty()){
            return R.error("邮箱为空，发送失败");
        }
        if(!CheckUtil.mailCheck(mailRequest.getSendTo())){
            return R.error("邮箱格式错误！");
        }

        mailRequest.setSubject("验证码");
        Random random = new Random();
        StringBuilder code= new StringBuilder();
        for(int i=0;i<6;i++) {
            int a = random.nextInt(10);
            code.append(a);
        }
        mailRequest.setText("<h2>欢迎使用GameBBS，您的验证码如下，请于10分钟内完成验证！</h2><br><br><center><h1>"+code+"</h1></center>");
        session.setAttribute("code",code);
        session.setMaxInactiveInterval(600);
        System.out.println(code);
        //sendMailService.sendHtmlMail(mailRequest);
        return R.success("发送成功！");
    }

}
