package com.csc380.codepeerreview.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DatabaseHelper {

    // Convert a list from one type to another
    public static <T, U> List<U> convertList(List<T> list, Function<T, U> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }
}
