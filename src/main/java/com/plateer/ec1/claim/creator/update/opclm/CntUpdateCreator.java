package com.plateer.ec1.claim.creator.update.opclm;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpClmInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

public interface CntUpdateCreator extends OpClmUpdateCreator{

    @Component
    class CancelCntUpdateCreator implements CntUpdateCreator{

        @Override
        public List<OpClmInfo> create(ClaimView claimView) {
            return createOpClmInfoList(claimView, ClaimGoodsInfo::toOpClmInfoOfCancelCnt);
        }

        @Override
        public List<ClaimBusiness> getTypes() {
            return Arrays.asList(GCC, MCA);
        }
    }

    @Component
    class RtgsCntUpdateCreator implements CntUpdateCreator{

        @Override
        public List<OpClmInfo> create(ClaimView claimView) {
            return createOpClmInfoList(claimView, ClaimGoodsInfo::toOpClmInfoOfRtgsCnt);
        }

        @Override
        public List<ClaimBusiness> getTypes() {
            return Arrays.asList(GRA, GEA);
        }
    }

    @Component
    @RequiredArgsConstructor
    class WithdrawalCntUpdateCreator implements CntUpdateCreator{
        private final ClaimMapper claimMapper;

        @Override
        public List<OpClmInfo> create(ClaimView claimView) {
            List<OpClmInfo> opClmInfoList = new ArrayList<>();

            opClmInfoList.addAll(getAcceptClmUpdateOpClmInfo(claimView));
            opClmInfoList.addAll(getOrgOrderUpdateOpClmInfo(claimView));

            return opClmInfoList;
        }

        private List<OpClmInfo> getAcceptClmUpdateOpClmInfo(ClaimView claimView){
            return createOpClmInfoList(claimView, ClaimGoodsInfo::toOpClmInfoOfCancelCnt);
        }

        private List<OpClmInfo> getOrgOrderUpdateOpClmInfo(ClaimView claimView){
            List<ClaimGoodsInfo> orgOpClmList = claimMapper.getOrgOpClmList(claimView.getClaimGoodsInfoList());
            return orgOpClmList.stream()
                    .map(ClaimGoodsInfo::toOpClmInfoOfZeroRtgsCnt)
                    .collect(Collectors.toList());
        }

        @Override
        public List<ClaimBusiness> getTypes() {
            return Arrays.asList(GRW, GEW);
        }
    }

}
