package com.ru.configuration.utiliedBot.utils;

import static com.ru.configuration.utiliedBot.utils.NumberValidator.isValid;

public class TextUtils {
    public static boolean isNumeric(String str){
        return isValid(str);
    }
}
