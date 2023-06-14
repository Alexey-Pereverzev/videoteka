package ru.gb.emailservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.dto.EmailDto;
import ru.gb.api.dtos.dto.UserNameMailDto;
import ru.gb.emailservice.exceptions.ResourceNotFoundException;
import ru.gb.emailservice.integrations.AuthServiceIntegration;

@Service
@RequiredArgsConstructor
public class MailService
{
    private final AuthServiceIntegration authServiceIntegration;
    private final JavaMailSender javaMailSender;

//    public void createMessage(Long id) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        try {
//            UserNameMailDto userDto = authServiceIntegration.findById(id);
//            message.setTo(userDto.getEmail());
//            //message.setFrom("Videoteka");
//            message.setSubject("Оформление заказа");
//            message.setText("Здравствуйте, " + userDto.getFirstName()+"!"+" \nВаш заказ успешно оформлен ");
//        } catch (Exception e) {
//            throw new ResourceNotFoundException("Пользователь не найден");
//        }
//        javaMailSender.send(message);
//    }

    public void sendMessage(EmailDto emailDto) {
        String email = emailDto.getEmail();
        String subject = emailDto.getSubject();
        String firstName = emailDto.getFirstName();
        String text = emailDto.getMessage();
        SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText("Здравствуйте, " + firstName+"! \n" + text);
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
    public String generateVerificationCode (String firstName, String email){
        int random = (int) (100000+(Math.random()*600000));
        String code = String.valueOf(random);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Код верификации");
        message.setText("Здравствуйте, " + firstName+ "! \nВаш код верификации - " + code);
        javaMailSender.send(message);
        return code;
    }

    public void composePasswordLetter(EmailDto emailDto){
        sendMessage(emailDto);
    }

    public void composeRegistrationLetter(String firstName, String userName, String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Регистрация пользователя ");
        message.setText("Здравствуйте, "+ firstName +  "! \nПоздравляем! Вы успешно зарегистрировались.  Ваш логин -  " + userName);
        javaMailSender.send(message);

    }
}

