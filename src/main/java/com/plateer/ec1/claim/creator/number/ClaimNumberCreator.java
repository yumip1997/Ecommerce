package com.plateer.ec1.claim.creator.number;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;
import static com.plateer.ec1.claim.enums.ClaimBusiness.GRW;

@Component
@RequiredArgsConstructor
public class ClaimNumberCreator implements MultiValueCustomFactory<ClaimBusiness>{

    private final ClaimMapper claimMapper;

    public String create(){
        return claimMapper.getClaimNo();
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(GCC, MCA, GRA, GRW);
    }
}
