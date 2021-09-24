package com.csc380.codepeerreview.util;

import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DatabaseHelper {

    // Convert a list from one type to another
    public static <T, U> List<U> convertList(List<T> list, Function<T, U> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }
}
