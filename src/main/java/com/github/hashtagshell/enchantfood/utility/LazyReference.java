package com.github.hashtagshell.enchantfood.utility;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.function.Function;
import java.util.function.Supplier;

public class LazyReference<T, R extends Reference<T>>
{
    private final Function<T, R> newRef;
    private final Supplier<T>    getVal;

    R ref;

    public LazyReference(Function<T, R> newRef, Supplier<T> getVal)
    {

        this.newRef = newRef;
        this.getVal = getVal;
    }

    public T get()
    {
        T val;
        if (ref == null || (val = ref.get()) == null)
            ref = newRef.apply(val = getVal.get());
        return val;
    }

    public static class Weak<T> extends LazyReference<T, WeakReference<T>>
    {
        public Weak(Supplier<T> getVal)
        {
            super(WeakReference::new, getVal);
        }
    }

    public static class Soft<T> extends LazyReference<T, SoftReference<T>>
    {
        public Soft(Supplier<T> getVal)
        {
            super(SoftReference::new, getVal);
        }
    }
}
