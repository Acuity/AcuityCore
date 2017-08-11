package com.acuity.db.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

/**
 * Created by Zachary Herridge on 8/4/2017.
 */
public class Json {

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter())
            .create();



}
