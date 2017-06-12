package com.acuity.rs.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

//Generated

public interface RSClassInfo extends RSNode {

    Field[] getFields();

    byte[][][] getMethodArgs();

    Method[] getMethods();
}
