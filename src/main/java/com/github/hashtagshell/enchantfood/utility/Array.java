package com.github.hashtagshell.enchantfood.utility;


import java.util.*;
import java.util.function.Function;

public class Array
{
    public static <T extends Comparable<? super T>> List<T> sort(List<T> list)
    {
        Collections.sort(list);
        return list;
    }

    public static <T> boolean contains(T[] array, T element)
    {
        for (T t : array)
            if (t.equals(element)) return true;
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <I, O> List<O> process(List<I> list, Function<I, O> function)
    {
        List<O> out = new ArrayList<>(list.size());
        list.forEach(in -> out.add(function.apply(in)));
        return out;
    }

    @SafeVarargs
    public static <T> Set<T> setOf(T... elements)
    {
        HashSet<T> set = new HashSet<>(elements.length);
        Collections.addAll(set, elements);
        return set;
    }

    public static <O> List<O> iterate(int start, int end, int step, Function<Integer, O> function)
    {
        List<O> out = new ArrayList<>();
        for (int i = start; i <= end; i += step)
            out.add(function.apply(i));
        return out;
    }
}
