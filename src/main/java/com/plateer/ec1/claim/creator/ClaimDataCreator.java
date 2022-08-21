package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.enums.define.OpClmInsertBase;
import com.plateer.ec1.claim.enums.define.OpClmUpdateBase;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
public class ClaimDataCreator {

    private final ClaimMapper claimMapper;

    public static ClaimDataCreator of(ClaimMapper claimMapper){
        return new ClaimDataCreator(claimMapper);
    }

    public OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> createOrdClmCreationVO(ClaimRequestVO claimRequestVO){
        ClaimDefine claimDefine = ClaimDefine.of(claimRequestVO);
        return OrdClmCreationVO.<ClaimInsertBase, ClaimUpdateBase>builder()
                .insertData(createClaimInsertBase(claimRequestVO.clone(), claimDefine))
                .updateData(createClaimUpdateBase(claimRequestVO.clone(), claimDefine))
                .build();
    }

    public ClaimInsertBase createClaimInsertBase(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine) {
        OpClmInsertBase opClmInsertBase = claimDefine.getOpClmInsertBase();
        String clmNo = opClmInsertBase.isCreateClaimNoFlag() ? claimMapper.getClaimNo() : claimRequestVO.getClmNo();

        return ClaimInsertBase.builder()
                .opClmInfoList(claimRequestVO.toInsertOpClmInfoList(opClmInsertBase, clmNo))
                .opOrdCostInfoList(claimRequestVO.toInsertOpOrdCostInfoList(clmNo))
                .opOrdBnfRelInfoList(claimRequestVO.toInsertOpOrdBnfRelInfoList(claimDefine.getOpBnfBase(), clmNo))
                .build();
    }

    public ClaimUpdateBase createClaimUpdateBase(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine){
        return ClaimUpdateBase.builder()
                .opClmInfoList(claimRequestVO.toUpdateOpClmInfoList(claimDefine.getOpClmUpdateBase(), () -> claimMapper.getOrgOpClm(claimRequestVO)))
                .opOrdCostInfoList(claimRequestVO.toUpdateOpOrdCostInfoList())
                .opOrdBnfInfoList(claimRequestVO.toUpdateOpOrdBnfInfo(claimDefine.getOpBnfBase()))
                .build();
    }

}
