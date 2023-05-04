package ru.gb.authorizationservice.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    private User(Builder builder) {
        setUsername(builder.username);
        setPassword(builder.password);
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
        setEmail(builder.email);
        setPhoneNumber(builder.phoneNumber);
        setAddress(builder.address);
        setRoles(builder.roles);
        setId(builder.id);
        setCreatedBy(builder.createdBy);
        setCreatedWhen(builder.createdWhen);
        setUpdateBy(builder.updateBy);
        setUpdateWhen(builder.updateWhen);
        setDeletedBy(builder.deletedBy);
        setDeletedWhen(builder.deletedWhen);
    }


    public static final class Builder {
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String address;
        private Collection<Role> roles;
        private Long id;
        private String createdBy;
        private LocalDateTime createdWhen;
        private String updateBy;
        private LocalDateTime updateWhen;
        private String deletedBy;
        private LocalDateTime deletedWhen;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withPassword(String val) {
            password = val;
            return this;
        }

        public Builder withFirstName(String val) {
            firstName = val;
            return this;
        }

        public Builder withLastName(String val) {
            lastName = val;
            return this;
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withPhoneNumber(String val) {
            phoneNumber = val;
            return this;
        }

        public Builder withAddress(String val) {
            address = val;
            return this;
        }

        public Builder withRoles(Collection<Role> val) {
            roles = val;
            return this;
        }

        public Builder withId(Long val) {
            id = val;
            return this;
        }

        public Builder withCreatedBy(String val) {
            createdBy = val;
            return this;
        }

        public Builder withCreatedWhen(LocalDateTime val) {
            createdWhen = val;
            return this;
        }

        public Builder withUpdateBy(String val) {
            updateBy = val;
            return this;
        }

        public Builder withUpdateWhen(LocalDateTime val) {
            updateWhen = val;
            return this;
        }

        public Builder withDeletedBy(String val) {
            deletedBy = val;
            return this;
        }

        public Builder withDeletedWhen(LocalDateTime val) {
            deletedWhen = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}



