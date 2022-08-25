package com.plateer.ec1.common.factory;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class MultiValueFactoryTemplate<K, V extends MultiValueCustomFactory<K>> {

    private final MultiValueMap<K, V> map = new LinkedMultiValueMap<>();

    public MultiValueFactoryTemplate(List<V> list){
        for (V v : list) {
            List<K> types = v.getTypes();
            for (K type : types) {
                map.add(type, v);
            }
        }
    }

    public List<V> getValues(K k) {
        return map.get(k);
    }
}
