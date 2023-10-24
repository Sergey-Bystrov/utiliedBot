package com.ru.configuration.utiliedBot.model;

import com.pengrad.telegrambot.request.SendPhoto;

public class messagePhotoDto {
    private SendPhoto photo;

    public messagePhotoDto(SendPhoto photo) {
        this.photo = photo;
    }

    public SendPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(SendPhoto photo) {
        this.photo = photo;
    }
}
