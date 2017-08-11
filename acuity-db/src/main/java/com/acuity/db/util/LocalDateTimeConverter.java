package com.acuity.db.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER =  DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString() + "Z");
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return FORMATTER.parse(json.getAsString(), LocalDateTime::from);
    }

    public static void main(String[] args) {
        String in = "2017-08-11T12:24:55.356Z";
        System.out.println(in);
        LocalDateTime out = FORMATTER.parse(in, LocalDateTime::from);
        System.out.println(out);
        String back = out.toString() + "Z";
        System.out.println(back);

    }
}