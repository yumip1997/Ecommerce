package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.enums.define.OpClmBase;
import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class ClaimDataCreator {

    public static OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> createOrdClmCreationVO(ClaimRequestVO claimRequestVO, Supplier<String> clmNoSupplier){
        ClaimDefine claimDefine = ClaimDefine.of(claimRequestVO);
        ClaimRequestVO clone = claimRequestVO.clone();
        return OrdClmCreationVO.<ClaimInsertBase, ClaimUpdateBase>builder()
                .insertData(createClaimInsertBase(clone, claimDefine.getOpClmBase(), clmNoSupplier))
                .updateData(createClaimUpdateBase(clone))
                .build();
    }

    public static ClaimInsertBase createClaimInsertBase(ClaimRequestVO claimRequestVO, OpClmBase opClmBase, Supplier<String> clmNoSupplier) {
        List<OpClmInfo> opClmInfoList = new ArrayList<>();
        List<OpOrdBnfRelInfo> opOrdBnfRelInfoList = new ArrayList<>();

        for (ClaimGoodsInfo claimGoodsInfo : claimRequestVO.getClaimGoodsInfoList()) {
            String clmNo = opClmBase.isCreateClaimNoFlag() ? clmNoSupplier.get() : claimGoodsInfo.getClmNo();

            opClmInfoList.addAll(claimGoodsInfo.toInsertOpClmInfoList(opClmBase, clmNo));
            opOrdBnfRelInfoList.addAll(claimGoodsInfo.toOpOrdBnfRelInfoList(clmNo));
        }

        return ClaimInsertBase.builder()
                .opClmInfoList(opClmInfoList)
                .opOrdBnfRelInfoList(opOrdBnfRelInfoList)
                .build();
    }

    public static ClaimUpdateBase createClaimUpdateBase(ClaimRequestVO claimRequestVO){
        for (ClaimGoodsInfo claimGoodsInfo : claimRequestVO.getClaimGoodsInfoList()) {


        }
        return new ClaimUpdateBase();
    }


}
