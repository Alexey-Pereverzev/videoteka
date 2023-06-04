package ru.gb.emailservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
