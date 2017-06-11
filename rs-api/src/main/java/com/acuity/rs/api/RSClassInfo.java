package com.acuity.rs.api;

//Generated

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface RSClassInfo extends RSNode {

    Field[] getFields();

    byte[][][] getMethodArgs();

    Method[] getMethods();
}
