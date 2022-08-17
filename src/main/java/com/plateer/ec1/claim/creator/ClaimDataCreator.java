package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.enums.define.OpClmBase;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class ClaimDataCreator {

    public static OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> createOrdClmCreationVO(ClaimRequestVO claimRequestVO, Supplier<String> clmNoSupplier){
        ClaimDefine claimDefine = ClaimDefine.of(claimRequestVO);
        return OrdClmCreationVO.<ClaimInsertBase, ClaimUpdateBase>builder()
                .insertData(createClaimInsertBase(claimRequestVO.clone(), claimDefine.getOpClmBase(), clmNoSupplier))
                .updateData(createClaimUpdateBase(claimRequestVO.clone()))
                .build();
    }

    public static ClaimInsertBase createClaimInsertBase(ClaimRequestVO claimRequestVO, OpClmBase opClmBase, Supplier<String> clmNoSupplier) {
        String clmNo = opClmBase.isCreateClaimNoFlag() ? clmNoSupplier.get() : claimRequestVO.getClmNo();

        return ClaimInsertBase.builder()
                .opClmInfoList(claimRequestVO.toInsertOpClmInfoList(opClmBase, clmNo))
                .opOrdBnfRelInfoList(claimRequestVO.toInsertOpOrdBnfRelInfoList(clmNo))
                .opOrdCostInfoList(claimRequestVO.toInsertOpOrdCostInfoList(clmNo))
                .build();
    }

    public static ClaimUpdateBase createClaimUpdateBase(ClaimRequestVO claimRequestVO){
        return new ClaimUpdateBase();
    }


}
