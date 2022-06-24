package com.plateer.ec1.common.factory;

public interface FactoryTemplate<T>{

    <T> T getType();

}
