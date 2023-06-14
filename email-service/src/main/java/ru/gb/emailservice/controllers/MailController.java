package ru.gb.emailservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.EmailDto;
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
    @PostMapping ()
    public ResponseEntity<?> sendMessage(@RequestBody EmailDto emailDto) {
        mailService.sendMessage(emailDto);
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
    @GetMapping ("/verification_code")
    public StringResponse composeVerificationLetter (@RequestParam String firstName, @RequestParam String email){
        return new StringResponse(mailService.generateVerificationCode(firstName,email));
    }



}
