package ru.gb.authorizationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.authorizationservice.entities.Role;
import ru.gb.authorizationservice.exceptions.ResourceNotFoundException;
import ru.gb.authorizationservice.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByTitle("ROLE_USER").orElseThrow(
                () -> new ResourceNotFoundException("Роль пользователя в базе не найдена"));
    }

    public Role getRoleByName(String role) {
        return roleRepository.findByTitle(role).orElseThrow(
                () -> new ResourceNotFoundException("Роль " + role + " в базе не найдена"));
    }
}
