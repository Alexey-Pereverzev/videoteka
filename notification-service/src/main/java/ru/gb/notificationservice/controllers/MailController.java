package ru.gb.notificationservice.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import ru.gb.notificationservice.services.MailService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping
    public SimpleMailMessage createMessage(@RequestHeader String userId) {
        Long userIDLong = Long.valueOf(userId);
        return mailService.createMessage(userIDLong);


    }

}