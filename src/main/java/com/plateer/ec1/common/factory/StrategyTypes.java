package com.plateer.ec1.common.factory;

import java.util.List;

public interface StrategyTypes<T> {

    List<T> getTypes();

    default boolean hasType(T t){
        return getTypes().contains(t);
    }

}
