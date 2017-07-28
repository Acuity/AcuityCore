package com.acuity.inject.replacements;

import com.acuity.api.AcuityInstance;
import com.acuity.api.annotations.ClientInvoked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Zachary Herridge on 7/6/2017.
 */
public class RReflection {

    private static Logger logger = LoggerFactory.getLogger(RReflection.class);

    @ClientInvoked
    public static String getMethodName(Method method){
        logger.warn("RS-Reflection getting name of method {}.", method);
        return method.getName();
    }

    @ClientInvoked
    public static Class[] getMethodParameterTypes(Method method){
        logger.warn("RS-Reflection finding method parameters {}.", method);
        return method.getParameterTypes();
    }

    @ClientInvoked
    public static Class getMethodReturnType(Method method) {
        logger.warn("RS-Reflection getting return-type of method {}.", method);
        return method.getReturnType();
    }

    @ClientInvoked
    public static int getMethodModifiers(Method method) {
        logger.warn("RS-Reflection getting modifiers of method {}.", method);
        return method.getModifiers();
    }

    @ClientInvoked
    public static Object invokeMethod(Method method, Object instance, Object[] params) throws InvocationTargetException, IllegalAccessException {
        logger.warn("RS-Reflection invoking method {} on instance {}, with params {}.", method, instance, Arrays.toString(params));
        return method.invoke(instance, params);
    }

    @ClientInvoked
    public static int getFieldInt(Field field, Object instance) throws IllegalAccessException {
        logger.warn("RS-Reflection getting field {} int value instance {}.", field, instance);
        return field.getInt(instance);
    }

    @ClientInvoked
    public static void setFieldInt(Field field, Object instance, int value) throws IllegalAccessException {
        logger.warn("RS-Reflection setting field {} instance {} to {}.", field, instance, value);
        field.setInt(instance, value);
    }

    @ClientInvoked
    public static int getFieldModifiers(Field field) {
        logger.warn("RS-Reflection getting modifiers of field {}.", field);
        return field.getModifiers();
    }

    @ClientInvoked
    public static ClassLoader getClassLoader(Class clazz){
        logger.warn("RS-Reflection getting classloader of class {}.", clazz);
        return clazz.getClassLoader();
    }

    @ClientInvoked
    public static Field getDeclaredField(Class clazz, String fieldName) throws NoSuchFieldException {
        logger.warn("RS-Reflection getting declared field '{}' from {}.", fieldName, clazz);
        return clazz.getDeclaredField(fieldName);
    }

    @ClientInvoked
    public static Method[] getDeclaredMethods(Class clazz){
        logger.warn("RS-Reflection getting declared methods from {}.", clazz);
        return clazz.getDeclaredMethods();
    }

    @ClientInvoked
    public static Class<?> classForName(String className) throws ClassNotFoundException {
        logger.debug("RS-Reflection getting class named '{}'.", className);
        try {
            return AcuityInstance.getAppletManager().getRsClassLoader().loadClass(className);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
