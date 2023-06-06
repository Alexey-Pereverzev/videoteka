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
    public String generateVerificationCode (String username, String email){
        String code = String.valueOf((int) (Math.random()*1000000));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Код верификации");
        message.setText("Здравствуйте, " + username + "! \nВаш код верификации - " + code);
        javaMailSender.send(message);
        return code;
    }

    public void composePasswordLetter(String email, String username, String password){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Смена пароля");
        message.setText("Здравствуйте, " + username + "! \nВаш новый пароль" + password);
        javaMailSender.send(message);
    }
}
