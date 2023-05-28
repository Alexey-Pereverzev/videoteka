package ru.gb.notificationservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.dto.UserDto;
import ru.gb.notificationservice.integrations.AuthServiceIntegration;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor

public class MailService
{
    private final AuthServiceIntegration authServiceIntegration;
    private final JavaMailSender javaMailSender;



//    //https://crontab.guru/
//    //http://www.cronmaker.com/?1
//    @Scheduled(cron = "0 0/1 * 1/1 * *") //Каждую минуту
//    public void sendMail() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo("ur email");
//        message.setSubject("TEST");
//        message.setText("TEST: " + LocalDateTime.now());
//        javaMailSender.send(message);
//        System.out.println("Scheduled task running");
//    }
    public SimpleMailMessage createMessage(Long id) {
        SimpleMailMessage message = new SimpleMailMessage();
        UserDto userDto= authServiceIntegration.findById(id);
        message.setTo(userDto.getEmail());
        message.setFrom("Videoteka");
        message.setSubject("Оформление заказа");
        message.setText("Здравсвуйте" + userDto.getFirstName());
        javaMailSender.send(message);
        return message;
    }
}
