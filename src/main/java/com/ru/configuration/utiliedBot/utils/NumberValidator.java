package com.ru.configuration.utiliedBot.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class NumberValidator {
    private static final Map<String, Predicate<Object>> predicates = new HashMap<>();
    private static final Pattern floatNumberPattern = Pattern.compile("\\d*\\.\\d*");

    static {
        addCheck("isNumeric", NumberValidator::isNumeric);
        addCheck("isFloat", NumberValidator::isFloat);
        addCheck("isInt", NumberValidator::isInt);
    }

    public static boolean isValid(Object checkObject) {
        return predicates.entrySet().stream()
                .anyMatch(p -> p.getValue().test(checkObject));
    }

    protected static void addCheck(String name, Predicate validate) {
        predicates.put(name, validate);
    }

    private static boolean isFloat(Object o) {
        if (o instanceof String) {
            return floatNumberPattern.asMatchPredicate().test((String) o);
        }
        return false;
    }

    private static boolean isInt(Object o) {
        if (o instanceof String) {
            return floatNumberPattern.asMatchPredicate().test((String) o);
        }
        return false;
    }

    private static boolean isNumeric(Object o) {
        if (o instanceof String str) {
            return str.matches("[0-9.]+");
        }
        return false;
    }
}
