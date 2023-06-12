package ru.gb.emailservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.emailservice.services.MailService;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
@Tag(name = "Оповещение", description = "Методы оповещения по почте")
public class MailController {
    private final MailService mailService;

    @Operation(
            summary = "Отправка сообщения",
            description = "Отправка сообщения"
    )
    @GetMapping ("/send")
    public ResponseEntity<?> createMessage(@RequestHeader String userId) {
        Long userIDLong = Long.valueOf(userId);
            mailService.createMessage(userIDLong);
            return ResponseEntity.ok(new StringResponse(" Письмо успешно отправлено"));
    }

    @Operation(
            summary = "Тест",
            description = "Тест"
    )
    @GetMapping ("/test")
    public StringResponse testMessage(@RequestHeader String email) {
        mailService.testMessage(email);
        return new StringResponse("Письмо успешно отправлено");
    }

    @Operation(
            summary = "Код верефикации",
            description = "Генерирует 6 ти значный код и отправляет пользователю"
    )
    @GetMapping ("/composeVerificationLetter")
    public StringResponse composeVerificationLetter (@RequestParam String firstName, @RequestParam String email){
        return new StringResponse(mailService.generateVerificationCode(firstName,email));
    }

    @Operation(
            summary = "Оповещение о смене пароля",
            description = "Оповещение о смене пароля"
    )
    @GetMapping ("/composePasswordLetter")
    public StringResponse composePasswordLetter(@RequestParam String email, @RequestParam String firstName){
        mailService.composePasswordLetter(email, firstName);
        return new StringResponse("Письмо о смене пароля успешно отправлено ");
    }
    @Operation(
            summary = "Регистрация пользователя",
            description = "Отправляем письмо на email  об успешной регистрации пользователя"
    )
    @GetMapping ("/composeRegistrationLetter")
    public StringResponse composeRegistrationLetter(@RequestParam String firstName, String userName, String email) {
        mailService.composeRegistrationLetter(firstName, userName, email);
        return new StringResponse("Письмо успешно отправлено");
    }

}
