package com.ru.configuration.utiliedBot.model;

import com.pengrad.telegrambot.request.SendAnimation;

public class messageAnimationDto {
    private SendAnimation animation;

    public messageAnimationDto(SendAnimation animation) {
        this.animation = animation;
    }

    public SendAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(SendAnimation animation) {
        this.animation = animation;
    }
}
