package com.github.hashtagshell.enchantfood.utility.tuple;

public class Triplet<K, N, V> extends Pair<K, V>
{
    private N node;

    public Triplet(K key, N node, V value)
    {
        super(key, value);
        this.node = node;
    }

    public N getNode()
    {
        return node;
    }

    public Triplet<K, N, V> setNode(N node)
    {
        this.node = node;
        return this;
    }

    @Override
    public Triplet<K, N, V> setKey(K key)
    {
        super.setKey(key);
        return this;
    }

    @Override
    public Triplet<K, N, V> setValue(V value)
    {
        super.setValue(value);
        return this;
    }

    @Override
    public Triplet<V, N, K> flip()
    {
        return new Triplet<>(getValue(), getNode(), getKey());
    }

    @Override
    public String toString()
    {
        return String.format("Triplet<%s, %s, %s>(%s, %s, %s)",
                getKey().getClass().getSimpleName(), getNode().getClass().getSimpleName(), getValue().getClass().getSimpleName(),
                getKey(), getNode(), getValue());
    }
}
