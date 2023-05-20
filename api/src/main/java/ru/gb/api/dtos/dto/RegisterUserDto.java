package ru.gb.api.dtos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель запроса на регистрацию пользователя")
public class RegisterUserDto {

    @Schema(description = "Имя пользователя", required = true, maxLength = 15, minLength = 3,
            example = "IvanPetrov1")
    private String username;

    @Schema(description = "Пароль", required = true, maxLength = 32, minLength = 6,
            example = "КириллицаИлиLatinitsa123")
    private String password;

    @Schema(description = "Повторный ввод пароля", required = true, maxLength = 32, minLength = 6,
            example = "КириллицаИлиLatinitsa123")
    private String confirmPassword;

    @Schema(description = "Email пользователя", required = true, example = "mail@gmail.com")
    private String email;

    @Schema(description = "Имя пользователя", required = false, example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия", required = false, example = "Петров")
    private String lastName;

    @Schema(description = "Телефон", required = false, example = "+79631478523")
    private String phoneNumber;

    @Schema(description = "Адрес", required = false, example = "Благовещенск, ул.Свободы д.25")
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RegisterUserDto() {
    }

    public RegisterUserDto(String username, String password, String confirmPassword,
                           String email, String firstName, String lastName, String phoneNumber,
                           String address) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}

