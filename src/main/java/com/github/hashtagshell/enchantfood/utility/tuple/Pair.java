package com.github.hashtagshell.enchantfood.utility.tuple;


import java.io.Serializable;

public class Pair<K, V> implements Serializable, Cloneable
{
    private K key;
    private V value;

    public Pair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public static <K, V> Pair<K, V> of(K key, V value)
    {
        return new Pair<>(key, value);
    }

    public K getKey()
    {
        return key;
    }

    public Pair<K, V> setKey(K key)
    {
        this.key = key;
        return this;
    }

    public V getValue()
    {
        return value;
    }

    public Pair<K, V> setValue(V value)
    {
        this.value = value;
        return this;
    }

    public Pair<K, V> clear()
    {
        this.key = null;
        this.value = null;
        return this;
    }

    public Pair<V, K> flip()
    {
        return new Pair<>(getValue(), getKey());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Pair)) return false;
        Pair pair = ((Pair) obj);
        return getKey().equals(pair.getKey()) && getValue().equals(pair.getValue());

    }

    @Override
    public String toString()
    {
        return String.format("Pair<%s, %s>(%s, %s)", key.getClass().getSimpleName(), value.getClass().getSimpleName(), key, value);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public Pair<K, V> clone()
    {
        return new Pair<>(getKey(), getValue());
    }
}