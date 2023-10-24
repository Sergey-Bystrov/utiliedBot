package com.ru.configuration.utiliedBot.model;

import com.pengrad.telegrambot.request.SendVideo;

public class messageVideoDto {
    private SendVideo video;

    public void setVideo(SendVideo video) {
        this.video = video;
    }

    public SendVideo getVideo() {
        return video;
    }

    public messageVideoDto(SendVideo video) {
        this.video = video;
    }
}
