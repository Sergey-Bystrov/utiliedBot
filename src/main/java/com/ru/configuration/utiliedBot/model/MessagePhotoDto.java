package com.ru.configuration.utiliedBot.model;

import com.pengrad.telegrambot.request.SendPhoto;

public class MessagePhotoDto {
    private SendPhoto photo;

    public MessagePhotoDto(SendPhoto photo) {
        this.photo = photo;
    }

    public SendPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(SendPhoto photo) {
        this.photo = photo;
    }
}
