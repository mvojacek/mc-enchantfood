package com.github.hashtagshell.enchantfood.utility;


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
}
