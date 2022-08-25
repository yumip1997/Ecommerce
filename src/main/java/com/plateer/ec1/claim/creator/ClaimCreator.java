package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.validation.groups.Claim;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;

import java.util.List;
import java.util.stream.Collectors;

public interface ClaimCreator<T, U> {

    List<ClaimDefine> groupingClaim();

    T create(U u);

    default boolean hasClaimDefine(ClaimDefine claimDefine) {
        return this.groupingClaim().stream()
                .anyMatch(e -> e == claimDefine);
    }

    static <T, U> List<U> create(List<T> list, List<? extends ClaimCreator<List<U>, List<T>>> creators) {
        return creators.stream()
                .map(c -> c.create(list))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

}
