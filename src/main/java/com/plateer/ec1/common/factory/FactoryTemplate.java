package com.plateer.ec1.common.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactoryTemplate<K,V extends CustomFactory> {

    private final Map<K, V> map = new HashMap<>();

    public FactoryTemplate(List<V> list){
        list.forEach(e -> map.put((K) e.getType(), e));
    }

    public V get(K k){
        return map.get(k);
    }
}
