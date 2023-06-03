package ru.gb.emailservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.dto.UserNameMailDto;
import ru.gb.emailservice.exceptions.ResourceNotFoundException;
import ru.gb.emailservice.integrations.AuthServiceIntegration;

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
    public void createMessage(Long id) {
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            UserNameMailDto userDto = authServiceIntegration.findById(id);
            message.setTo(userDto.getEmail());
            //message.setFrom("Videoteka");
            message.setSubject("Оформление заказа");
            message.setText("Здравствуйте, " + userDto.getFirstName()+"!"+" \nВаш заказ успешно оформлен ");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Пользователь не найден");
        }
        javaMailSender.send(message);
    }

    public void testMessage(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        // message.setFrom("Videoteka");
        message.setSubject("Оформление заказа");
        message.setText("Здравствуйте, Анна! \nВаш заказ успешно оформлен ");
        javaMailSender.send(message);

    }
}
