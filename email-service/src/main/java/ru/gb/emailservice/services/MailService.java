package ru.gb.emailservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.dto.EmailDto;


@Service
@RequiredArgsConstructor
public class MailService
{
    private final JavaMailSender javaMailSender;



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
        EmailDto emailDto = new EmailDto();
        emailDto.setEmail(email);
        emailDto.setFirstName(firstName);
        emailDto.setSubject("Код верификации");
        int random = (int) (100000+(Math.random()*600000));
        String code = String.valueOf(random);
        SimpleMailMessage message = new SimpleMailMessage();
        emailDto.setMessage("Ваш код верификации - " + code);
        sendMessage(emailDto);
        return code;
    }


}
