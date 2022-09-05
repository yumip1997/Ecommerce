package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;

import java.util.List;
import java.util.stream.Collectors;

public interface ClaimCreator<T, U> extends MultiValueCustomFactory<ClaimBusiness> {

    T create(U u);

    static <T, U> List<U> create(List<T> list, List<? extends ClaimCreator<List<U>, List<T>>> creators) {
        return creators.stream()
                .map(c -> c.create(list))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    static <T, U> List<U> create(T obj, List<? extends ClaimCreator<List<U>, T>> creators) {
        return creators.stream()
                .map(c -> c.create(obj))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
