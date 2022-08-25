package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

public class ClaimNumberCreator implements ClaimCreator<String, Supplier<String>> {

    private static final ClaimNumberCreator claimNumberCreator = new ClaimNumberCreator();

    public static ClaimNumberCreator of(){
        return claimNumberCreator;
    }

    private ClaimNumberCreator(){}

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(GCC, MCA, GRA, GRW);
    }

    @Override
    public String create(Supplier<String> stringSupplier) {
        return stringSupplier.get();
    }
}
