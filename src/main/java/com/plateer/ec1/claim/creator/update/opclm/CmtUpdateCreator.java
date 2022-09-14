package com.plateer.ec1.claim.creator.update.opclm;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpClmInfo;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

public interface CmtUpdateCreator extends OpClmUpdateCreator{

    @Component
    class CancelCmtUpdateCreator implements OpClmUpdateCreator{

        @Override
        public List<OpClmInfo> create(ClaimView claimView) {
            return createOpClmInfoList(claimView, ClaimGoodsInfo::toOpClmInfoCancelComplete);
        }

        @Override
        public List<ClaimBusiness> getTypes() {
            return Collections.singletonList(MCC);
        }
    }
}
