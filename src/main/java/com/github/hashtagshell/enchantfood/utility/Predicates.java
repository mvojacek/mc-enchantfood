package com.github.hashtagshell.enchantfood.utility;


import java.util.List;
import java.util.function.Predicate;

public class Predicates
{
    public static class SimplePredicate<T> implements Predicate<T>
    {
        private boolean allow;

        public SimplePredicate(boolean allow)
        {
            this.allow = allow;
        }

        @Override
        public boolean test(T input)
        {
            return allow;
        }
    }

    public abstract static class NotNullPredicate<T> implements Predicate<T>
    {
        private boolean ifNull = false;

        public NotNullPredicate()
        {
        }

        public NotNullPredicate(boolean ifNull)
        {
            this.ifNull = ifNull;
        }

        @Override
        public final boolean test(T input)
        {
            return input == null ? ifNull : apply(input);
        }

        public abstract boolean apply(T input);
    }

    public static <T> boolean and(List<Predicate<T>> list, T t)
    {
        for (Predicate<T> p : list)
            if (!p.test(t)) return false;
        return true;
    }

    public static <T> boolean or(List<Predicate<T>> list, T t)
    {
        for (Predicate<T> p : list)
            if (p.test(t)) return true;
        return false;
    }
}
