package com.gameForum.controller;


import cn.hutool.crypto.digest.MD5;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameForum.common.R;
import com.gameForum.entity.User;
import com.gameForum.entity.UserDto;
import com.gameForum.service.UserService;
import com.gameForum.utils.CheckUtil;
import com.gameForum.utils.IsPhone;
import com.gameForum.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理接口")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据id查询用户
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询用户")
    public R<UserDto> getById(@PathVariable Integer id){
        User user = userService.getById(id);
        if(user==null){
            return R.nferror("用户不存在！");
        }
        UserDto userDto = userService.getUserInfo(id);
        BeanUtils.copyProperties(user,userDto);
        userDto.setPassword(null);
        return R.success(userDto);
    }

    /**
     * 批量获取用户
     */
    @GetMapping("/list")
    @ApiOperation("批量获取用户")
    public R<List<User>> getList(String name){
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name!=null,User::getName,name);

        List<User> list = userService.list(lambdaQueryWrapper);
        return R.success(list);
    }

    /**
     * 检测用户名重复
     */
    @GetMapping("/repeat")
    @ApiOperation("检测用户名是否重复")
    public R<String> isRepeat(Integer type,String str){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(type==1){

            lambdaQueryWrapper.eq(User::getUsername,str);
        } else if (type==2) {
            lambdaQueryWrapper.eq(User::getEmail,str);
        }
        User user = userService.getOne(lambdaQueryWrapper);
        if(user!=null)
        {
            if(type==1) {
                return R.error("用户名已存在");
            }
            else if(type==2){
                return R.error("该邮箱已被注册");
            }
        }

        return R.success("无重复");
    }


    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public R<String> register(@RequestBody UserDto user,HttpSession session){
        //校验手机号格式
        if(!CheckUtil.phoneCheck(user.getPhone()))
        {
            return R.error("请输入正确的手机号码");
        }
        if(!CheckUtil.mailCheck(user.getEmail())){
            return R.error("邮箱格式错误");
        }
        if(!CheckUtil.usernameCheck(user.getUsername())){
            return R.error("用户名不合法");
        }
        if(!CheckUtil.passwordCheck(user.getPassword())){
            return R.error("密码格式错误");
        }

        if(session.getAttribute("code")==null) {
            return R.error("验证码超时，请重新发送邮箱验证码");
        }
        String getCode =  session.getAttribute("code").toString();
        if(user.getCode()==null){
            return R.error("请输入邮箱验证码");
        }
        if(!user.getCode().equals(getCode)){
            return R.error("邮箱验证码错误");
        }
        if(session.getAttribute("verifyCode")==null){
            return R.error("验证码超时，请刷新验证码");
        }
        String getVerityCode = session.getAttribute("verifyCode").toString();
        if(user.getVerifyCode()==null){
            return R.error("请输入验证码");
        }
        if(!user.getVerifyCode().equals(getVerityCode)){
            return R.error("验证码错误");
        }
        //检测是否被注册
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        LambdaQueryWrapper<User> lambdaQueryWrapper1 =new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(User::getEmail, user.getEmail());
        int count = Math.toIntExact(userService.count(lambdaQueryWrapper));
        if(count>0)
            return R.error("该用户名已被注册");
        int count1 = Math.toIntExact(userService.count(lambdaQueryWrapper1));
        if(count1>0)
            return R.error("该邮箱已被注册");
        String password = user.getPassword()+user.getUsername();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);

        User userAdd = new User();
        BeanUtils.copyProperties(user,userAdd);
        System.out.println(userAdd);
        return R.success("成功");
        //userService.save(userAdd);
        //return R.success("添加成功!");
    }

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    @ApiOperation("用户登陆")
    public R<User> login(@RequestBody UserDto user, HttpServletResponse response,HttpSession session){
        //验证是否用户名以及手机号都为空
        if(user.getUsername()==null)
            return R.error("请输入用户名");
        if(session.getAttribute("verifyCode")==null){
            return R.error("验证码超时，请刷新验证码");
        }
        String getVerityCode = session.getAttribute("verifyCode").toString();
        if(user.getVerifyCode()==null){
            return R.error("请输入验证码");
        }
        if(!user.getVerifyCode().equals(getVerityCode)){
            return R.error("验证码错误");
        }
        //添加条件
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(user.getUsername()!=null,User::getUsername,user.getUsername());
        lambdaQueryWrapper.eq(user.getPhone()!=null,User::getPhone,user.getPhone());
        //获取用户
        User userh= userService.getOne(lambdaQueryWrapper);

        if (userh!=null)
        {
            if(userh.getStatus()==1)
                return R.error("用户封禁中!");
            String password = user.getPassword()+userh.getUsername();
            password= DigestUtils.md5DigestAsHex(password.getBytes());
            if(password.equals(userh.getPassword())) {
                String token = TokenUtil.tokenCreate(userh.getId());
                response.setHeader("Access-Control-Expose-Headers", "Authorization");
                response.addHeader("Authorization",token);
                return R.success(userh);
            }
            return R.error("登陆失败，请检查用户名或密码是否正确！");
        }
        return R.error("登陆失败，请检查用户名或密码是否正确！");
    }

    /**
     * 更新信息
     */
    @PutMapping("/update")
    @ApiOperation("修改信息")
    public R<String> update(@RequestBody User user,HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
            }
            else{
                return R.loginError("请登录!");
            }
        }
        else{
            return R.loginError("请登录!");
        }
        if(!Objects.equals(userId, user.getId())){
            return R.error("异常操作，请重试！");
        }
        if(user.getPassword()!=null){
            if(!CheckUtil.passwordCheck(user.getPassword())){
                return R.error("密码格式错误");
            }
            User user1 = userService.getById(user.getId());
            String password = user.getPassword()+user.getUsername();
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            if(Objects.equals(user1.getPassword(), password)){
                return R.error("新密码与旧密码不可相同");
            }
            user.setPassword(password);
        }
        userService.updateById(user);
        return R.success("修改成功!");
    }

    @PutMapping("/resetPwd")
    @ApiOperation("重置密码")
    public R<String> update(@RequestBody UserDto userDto,HttpSession session){
        if(!CheckUtil.mailCheck(userDto.getEmail())){
            return R.error("邮箱格式错误");
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail, userDto.getEmail());
        User user = userService.getOne(lambdaQueryWrapper);
        if(user==null){
            return R.nferror("该邮箱账号不存在！");
        }

        if(session.getAttribute("code")==null) {
            return R.error("验证码超时，请重新发送邮箱验证码");
        }
        String getCode =  session.getAttribute("code").toString();
        if(userDto.getCode()==null){
            return R.error("请输入邮箱验证码");
        }
        if(!userDto.getCode().equals(getCode)){
            return R.error("邮箱验证码错误");
        }
        if(!CheckUtil.passwordCheck(userDto.getPassword())){
            return R.error("密码格式错误");
        }
        String password = userDto.getPassword()+user.getUsername();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(Objects.equals(user.getPassword(), password)){
            return R.error("新密码与旧密码不可相同");
        }
        user.setPassword(password);
        userService.updateById(user);
        return R.success("修改成功!");
    }

    /**
     * 注销用户
     */
    @DeleteMapping("/delete")
    @ApiOperation("注销账户")
    public R<String> delete(@RequestBody User user){
        userService.removeById(user);
        return R.success("注销成功");
    }

    /**
     * 解封用户
     */
    @PutMapping("/status/1")
    @ApiOperation("解封用户")
    public R<String> enabled(@RequestBody User user){
        user.setStatus(0);
        userService.updateById(user);
        return R.success("解封成功!");
    }

    /**
     * 封禁用户
     */
    @PutMapping("/status/0")
    @ApiOperation("禁用用户")
    public R<String> disabled(@RequestBody User user){
        user.setStatus(1);
        userService.updateById(user);
        return R.success("封禁成功!");
    }


    @PutMapping("/integral/add")
    @ApiOperation("积分增加")
    public R<String> addScore(HttpServletRequest request,Integer num){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
            }
            else {
                return R.success("未登录，不予记录");
            }
        }
        else{
            return R.success("未登录，不予记录");
        }
        User user = userService.getById(userId);
        if(user==null)
            return R.error("用户异常");
        user.setIntegral(user.getIntegral()+num);
        userService.updateById(user);
        return R.success("操作成功!");
    }

    @PutMapping("/integral/reduce")
    @ApiOperation("积分减少")
    public R<String> reduceScore(HttpServletRequest request,
                                 @RequestParam(value = "num",required = false,defaultValue = "5") Integer num){

        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
            }
            else {
                return R.success("未登录，不予记录");
            }
        }
        else{
            return R.success("未登录，不予记录");
        }
        User user = userService.getById(userId);

        if(user==null)
            return R.error("用户异常");
        if(user.getIntegral()-num<0){
            return R.error("操作异常!");
        }
        user.setIntegral(user.getIntegral()-num);
        userService.updateById(user);
        return R.success("操作成功!");
    }

    @PutMapping("/level")
    @ApiOperation("经验增加")
    public R<String> addLevel(Integer num,Integer dayExp, HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
            }
            else {
                return R.success("未登录，不予记录");
            }
        }
        else{
            return R.success("未登录，不予记录");
        }
        User user = userService.getById(userId);
        if(user==null)
            return R.error("用户异常");
        if(Objects.equals(user.getDayLevel(), dayExp)){
            return R.success("已达经验上限！");
        }
        if(user.getDayLevel()+num>dayExp){
            num = dayExp-user.getDayLevel();
        }
        user.setLevel(user.getLevel()+num);
        user.setDayLevel(user.getDayLevel()+num);
        userService.updateById(user);
        return R.success("操作成功！");
    }


}

