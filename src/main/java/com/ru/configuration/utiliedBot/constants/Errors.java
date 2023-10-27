package com.ru.configuration.utiliedBot.constants;

import java.util.HashMap;
import java.util.Map;

public class Errors {
    public static Map<String, String> errors = Map.of(
            "HANDLE_VIDEO_ERROR","Произошла ошибка при обработке или отправке видео от пользователя",
            "HANDLE_PHOTO_ERROR","Произошла ошибка при обработке или отправке фото от пользователя"
    );
}
