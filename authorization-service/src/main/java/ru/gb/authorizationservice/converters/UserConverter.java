package ru.gb.authorizationservice.converters;

import org.springframework.stereotype.Component;
import ru.gb.api.dtos.dto.UserDto;
import ru.gb.authorizationservice.entities.User;

@Component
public class UserConverter {
    public UserDto entityToDto(User user) {
        UserDto result = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getTitle())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .isDeleted(user.isDeleted())
                .build();
        return result;
    }
}
