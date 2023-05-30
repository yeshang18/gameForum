package com.gameForum.common;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameForum.entity.User;
import com.gameForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Configuration
public class ScheduledUtil {
    @Autowired
     private UserService userService;

    @Scheduled(cron = "0 0 0 * * ?")
    private void setDaYLevel(){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        User user = new User();
        user.setDayLevel(0);
        userService.update(user,lambdaQueryWrapper);
        System.out.println("重置每日经验完成！");
    }
}
