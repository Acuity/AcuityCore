package com.acuity.api.rs.utils;

import java.util.regex.Pattern;

/**
 * Created by Zachary Herridge on 11/21/2017.
 */
public class FormatUtil {

    private static Pattern formatPattern = Pattern.compile("<.+?>");

    public static String format(final String input) {
        if (input == null) return null;
        return formatPattern.matcher(input).replaceAll("").replaceAll("\\p{javaSpaceChar}", " ");
    }
}
