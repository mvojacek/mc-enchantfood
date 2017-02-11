package com.github.hashtagshell.enchantfood.utility.tuple;

public class Quadruplet<K, N, Q, V> extends Triplet<K, N, V>
{
    private Q quark;

    public Quadruplet(K key, N node, Q quark, V value)
    {
        super(key, node, value);
        this.quark = quark;
    }

    public Q getQuark()
    {
        return quark;
    }

    public Quadruplet<K, N, Q, V> setQuark(Q quark)
    {
        this.quark = quark;
        return this;
    }

    @Override
    public Quadruplet<K, N, Q, V> setNode(N node)
    {
        super.setNode(node);
        return this;
    }

    @Override
    public Quadruplet<K, N, Q, V> setKey(K key)
    {
        super.setKey(key);
        return this;
    }

    @Override
    public Quadruplet<K, N, Q, V> setValue(V value)
    {
        super.setValue(value);
        return this;
    }

    @Override
    @Deprecated
    public Quadruplet<V, N, Q, K> flip()
    {
        return new Quadruplet<>(getValue(), getNode(), getQuark(), getKey());
    }

    @Override
    public String toString()
    {
        return String.format("Quadruplet<%s, %s, %s, %s>(%s, %s, %s, %s)",
                getKey().getClass().getSimpleName(), getNode().getClass().getSimpleName(),
                getQuark().getClass().getSimpleName(), getValue().getClass().getSimpleName(),
                getKey(), getNode(), getQuark(), getValue());
    }
}
