package ru.gb.authorizationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.authorizationservice.entities.Role;
import ru.gb.authorizationservice.exceptions.ResourceNotFoundException;
import ru.gb.authorizationservice.repositories.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByTitle("ROLE_USER").orElseThrow(
                () -> new ResourceNotFoundException("Роль пользователя в базе не найдена"));
    }

    public Optional<Role> getRoleByName(String role) {
        return roleRepository.findByTitle(role);
    }


}