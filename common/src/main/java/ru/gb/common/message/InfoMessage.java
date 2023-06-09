package ru.gb.common.message;

public enum InfoMessage {

    FILE_NOT_FOUND("Файл не найден");
    //Новые строки добавлять сюда!!!
    String infoMessage;
    InfoMessage (String infoMessage){
        this.infoMessage=infoMessage;
    }

    public String getInfoMessage() {
        return infoMessage;
    }
}
