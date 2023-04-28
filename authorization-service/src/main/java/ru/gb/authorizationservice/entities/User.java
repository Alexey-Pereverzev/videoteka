package ru.gb.authorizationservice.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="users")
@Data
@RequiredArgsConstructor
public class User extends GenericEntity {
    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phoneNumber;

    @Column(name="address")
    private String address;

    @ManyToMany
    @JoinTable(name="user_roles",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Collection<Role> roles;
}



