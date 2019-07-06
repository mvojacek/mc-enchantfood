package com.github.hashtagshell.enchantfood.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Reflection
{
    /**
     * Retrieves all fields (all access levels) from all classes up the class
     * hierarchy starting with {@code startClass} stopping with and not
     * including {@code exclusiveParent}.
     * <p>
     * Generally {@code Object.class} should be passed as
     * {@code exclusiveParent}.
     *
     * @param startClass      the class whose fields should be retrieved
     * @param exclusiveParent if not null, the base class of startClass whose fields should
     *                        not be retrieved.
     * @return The fields
     */
    public static List<Field> getFieldsUpTo(Class<?> startClass, Class<?> exclusiveParent)
    {
        List<Field> currentClassFields = Arrays.asList(startClass.getDeclaredFields());
        Class<?> parentClass = startClass.getSuperclass();

        if (parentClass != null && (exclusiveParent == null || !(parentClass.equals(exclusiveParent))))
        {
            List<Field> parentClassFields = getFieldsUpTo(parentClass, exclusiveParent);
            currentClassFields.addAll(parentClassFields);
        }

        return currentClassFields;
    }

    public static List<Method> getMethodsUpTo(Class<?> startClass, Class<?> exclusiveParent)
    {
        List<Method> currentClassMethod = Arrays.asList(startClass.getDeclaredMethods());
        Class<?> parentClass = startClass.getSuperclass();

        if (parentClass != null && (exclusiveParent == null || !(parentClass.equals(exclusiveParent))))
        {
            List<Method> parentClassFields = getMethodsUpTo(parentClass, exclusiveParent);
            currentClassMethod.addAll(parentClassFields);
        }

        return currentClassMethod;
    }
}
