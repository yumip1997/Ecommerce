package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.define.ClaimDefine;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.plateer.ec1.claim.enums.define.ClaimDefine.*;

public class ClaimNumberCreator implements ClaimCreator<String, Supplier<String>> {

    private static final ClaimNumberCreator claimNumberCreator = new ClaimNumberCreator();

    public static ClaimNumberCreator of(){
        return claimNumberCreator;
    }

    @Override
    public List<ClaimDefine> groupingClaim() {
        return Arrays.asList(GCC, MCA, GRA, GRW);
    }

    @Override
    public String create(Supplier<String> stringSupplier) {
        return stringSupplier.get();
    }
}
