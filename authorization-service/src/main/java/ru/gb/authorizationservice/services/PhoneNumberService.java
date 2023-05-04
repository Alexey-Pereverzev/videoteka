package ru.gb.authorizationservice.services;

import java.util.ArrayList;

public class PhoneNumberService {                 //  класс допустимых символов для номера телефона
    private static ArrayList<Character> characters;

    public PhoneNumberService() {
        characters = new ArrayList<>(14);
        characters.add('0'); characters.add('1'); characters.add('2'); characters.add('3'); characters.add('4'); characters.add('5');
        characters.add('6'); characters.add('7'); characters.add('8'); characters.add('9'); characters.add(' '); characters.add('-');
        characters.add('('); characters.add(')'); characters.add('+');
    }

    public static ArrayList<Character> getCharacters() {
        return characters;
    }

    public static boolean acceptablePhoneSymbols(String phoneNumber) {
        if (phoneNumber==null) {
            return true;
        }
        if (phoneNumber.isBlank()) {
            return true;
        }
        char[] chars = phoneNumber.toCharArray();
        for (char aChar : chars) {
            if (!PhoneNumberService.getCharacters().contains(aChar)) {
                return false;
            }
        }
        return true;
    }

    public static boolean acceptablePhoneNumber(String phoneNumber) {
        if (!phoneNumber.isEmpty() && !phoneNumber.isBlank()) {
            if (acceptablePhoneSymbols(phoneNumber)) {               //  если нет недопустимых символов
                phoneNumber = phoneNumber.replace(" ", "").replace("(", "").replace(")", "")
                        .replace("-", "");       //  выкидываем символы кроме цифр
                int length = phoneNumber.length();
                if ((length < 7) || (length > 13)) {                //  проверяем длину номера - должна быть от 7 до 13
                    return (false);
                }
            } else {
                return (false);
            }
        }
        return true;
    }


}
