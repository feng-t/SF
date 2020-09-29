package com.sf.demo;

import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.Map;

public interface MultiValueMap<K, V> extends Map<K, List<V>> {
    @Nullable
    V getFirst(K var1);

    void add(K var1, @Nullable V var2);

    void addAll(K var1, List<? extends V> var2);

    void addAll(MultiValueMap<K, V> var1);

    void set(K var1, @Nullable V var2);

    void setAll(Map<K, V> var1);

    Map<K, V> toSingleValueMap();
}