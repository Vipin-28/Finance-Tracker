package com.vipinkumarx28.sboot.event.listener;

import com.vipinkumarx28.sboot.entities.User;
import com.vipinkumarx28.sboot.event.RegistrationCompleteEvent;
import com.vipinkumarx28.sboot.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;


    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // create the verification token for the user with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        //send mail to the user
        String url = event.getApplicationUrl() +
                "verificationToken?token="+
                token;
        // sendVerificationEmail()
        System.out.println("Click the link to verify your account:"+ url);
    }
}
