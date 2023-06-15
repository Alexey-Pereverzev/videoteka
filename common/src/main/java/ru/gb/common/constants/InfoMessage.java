package ru.gb.common.constants;

public interface InfoMessage {

    String FILE_NOT_FOUND = "Файл не найден";
    String RESOURCE_NOT_FOUND_CODE = "RESOURCE_NOT_FOUND";
    String USERNAME_NOT_FOUND_CODE = "USERNAME_NOT_FOUND";
    String CHECK_USERNAME_PASSWORD_ERROR_CODE = "CHECK_USERNAME_PASSWORD_ERROR";
    String INPUT_DATA_ERROR_CODE = "INPUT_DATA_ERROR";
    String USER_ALREADY_EXISTS_CODE = "USER_ALREADY_EXISTS";
    String TOKEN_IS_EXPIRED_CODE = "TOKEN_IS_EXPIRED";
    String TOKEN_IS_MALFORMED_CODE = "TOKEN_IS_MALFORMED";
    String INVALID_SIGNATURE_CODE = "INVALID_SIGNATURE";
    String UNSUPPORTED_JWT_TOKEN_CODE = "UNSUPPORTED_JWT_TOKEN";
    String PUBLIC_KEY_ERROR_CODE = "PUBLIC_KEY_ERROR";
    String ILLEGAL_ARGUMENT_CODE = "ILLEGAL_ARGUMENT";
    String INTEGRATION_ERROR_CODE = "INTEGRATION_ERROR";
    String TOKEN_CREATED_SUCCESSFULLY = "Токен успешно создан";
    String INVALID_USERNAME_OR_PASSWORD = "Некорректный логин или пароль";
    String USER_NOT_FOUND = "Пользователь не найден";
    String ROLE_NOT_FOUND = "Роль пользователя в базе не найдена";
    String ORDER_CREATED_SUCCESSFULLY = "Заказ успешно создан";
    String INPUT_DATA_ERROR = "Ошибка ввода данных";
    String ROLE_CHANGED_SUCCESSFULLY = "Роль успешно изменена";
    String USER_DELETED_SUCCESSFULLY = "Пользователь успешно удален";
    String USER_ALREADY_EXISTS = "Такой пользователь уже есть в системе";
    String ADMIN_NOT_FOUND = "Админ не найден в базе по id";
    String USER_FOUND = "Пользователь найден, вернули результат";
    String FULLNAME = "Имя и фамилия";
    String CONFIRMATION_CODE_SENT = "Код подтверждения отправлен на емэйл";
    String INCORRECT_EMAIL = "Некорректный емэйл";
    String CORRECT_CODE = "Код правильный";
    String INCORRECT_CODE = "Код некорректный, повторите попытку";
    String TRY_AGAIN = "Ошибка, повторите попытку";
    String TIME_IS_UP = "Время истекло, повторите попытку";
    String INCORRECT_PHONE = "Некорректный номер телефона";
    String PASSWORD_UPDATE = "Смена пароля";
    String PASSWORD_UPDATED_SUCCESSFULLY = "Вы успешно сменили пароль";
    String PASSWORD_VERIFICATION_ERROR = "Ошибка проверки пароля";
    String EMAIL_SERVICE_ERROR = "Ошибка сервиса уведомлений";
    String EMAIL_CANNOT_BE_EMPTY = "Email не может быть пустым";
    String LOGIN_CANNOT_BE_EMPTY = "Логин не может быть пустым";
    String PASSWORD_CANNOT_BE_EMPTY = "Пароль не может быть пустым";
    String FIRSTNAME_CANNOT_BE_EMPTY = "ERROR: пустое имя";
    String LASTNAME_CANNOT_BE_EMPTY = "ERROR: пустая фамилия";
    String INVALID_EMAIL_CHARACTERS = "Недопустимые символы в email";
    String INVALID_LOGIN_CHARACTERS = "Недопутсимые символы в логине. Допустимы латинские буквы A-Z, a-z и цифры 0-9";
    String INVALID_PASSWORD_CHARACTERS = "Недопутсимые символы в пароле. Допустимы латинские буквы A-Z, a-z, символы кириллицы А-Я, а-я и цифры 0-9";
    String INVALID_FIRSTNAME = "Ошибка ввода: имя должно быть введено полностью на кириллице или на латинице";
    String INVALID_LASTNAME = "Ошибка ввода: фамилия должна быть введена полностью на кириллице или на латинице";
    String PASSWORD_MISMATCH = "Пароли не совпадают";
    String SIGN_UP = "Регистрация пользователя";


}
