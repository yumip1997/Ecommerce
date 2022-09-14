package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.creator.number.ClaimNumberCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimCreatorVO;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClaimDataCreatorManager {

    private final ClaimNumberCreator claimNumberCreator;

    public OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> createOrdClmCreationVO(ClaimView claimView, ClaimBusiness claimBusiness, ClaimCreatorVO claimCreatorVO){
        String clmNo = claimNumberCreator.hasType(claimBusiness) ? claimNumberCreator.create() : claimView.getClmNo();

        return OrdClmCreationVO.<ClaimInsertBase, ClaimUpdateBase>builder()
                .insertData(createClaimInsertBase(claimView.clone(), clmNo, claimCreatorVO))
                .updateData(createClaimUpdateBase(claimView.clone(), claimCreatorVO))
                .clmNo(clmNo)
                .ordNo(claimView.getOrdNo())
                .build();
    }

    private ClaimInsertBase createClaimInsertBase(ClaimView claimView, String clmNo, ClaimCreatorVO claimCreatorVO) {
        ClaimInsertBase claimInsertBase = ClaimInsertBase.builder()
                .opClmInfoList(ClaimDataCreator.createClaimData(claimView, claimCreatorVO.getOpClmInsertCreators()))
                .opOrdCostInfoList(ClaimDataCreator.createClaimData(claimView, claimCreatorVO.getOpCostInsertCreators()))
                .opOrdBnfRelInfoList(ClaimDataCreator.createClaimData(claimView, claimCreatorVO.getOpBnfRelInsertCreators()))
                .build();

        claimInsertBase.setClmNo(clmNo);
        return claimInsertBase;
    }

    private ClaimUpdateBase createClaimUpdateBase(ClaimView claimView, ClaimCreatorVO claimCreatorVO){
        return ClaimUpdateBase.builder()
                .opClmInfoList(ClaimDataCreator.createClaimData(claimView, claimCreatorVO.getOpClmUpdateCreators()))
                .opOrdCostInfoList(ClaimDataCreator.createClaimData(claimView, claimCreatorVO.getOpCostUpdateCreators()))
                .opOrdBnfInfoList(ClaimDataCreator.createClaimData(claimView, claimCreatorVO.getOpBnfInfoUpdateCreators()))
                .build();
    }
}
