package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.factory.StrategyTypes;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface ClaimDataCreator<T> extends StrategyTypes<ClaimBusiness> {

    T create(ClaimView claimView);

    static <T,U extends ClaimDataCreator<List<T>>> List<T> createClaimData(ClaimView claimView, List<U> creators){
        if(CollectionUtils.isEmpty(creators)) return Collections.emptyList();

        return creators.stream()
                .map(c -> c.create(claimView))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
