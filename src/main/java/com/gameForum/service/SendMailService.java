package com.gameForum.service;

import com.gameForum.entity.MailRequest;

public interface SendMailService {
    void sendSimpleMail(MailRequest mailRequest);

    void sendHtmlMail(MailRequest mailRequest);
}
