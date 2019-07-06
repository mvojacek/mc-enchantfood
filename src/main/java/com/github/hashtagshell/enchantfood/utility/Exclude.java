package com.github.hashtagshell.enchantfood.utility;


import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exclude
{
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @interface Inherit
    {}
}
