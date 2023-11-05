package com.ru.configuration.utiliedBot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ButtonModel {
    private String title;
    private String url;
    private int height;
    private int width;
}
