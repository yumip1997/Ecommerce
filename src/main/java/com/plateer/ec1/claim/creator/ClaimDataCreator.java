package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.enums.define.OpClmInsertBase;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.vo.*;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        ClaimInsertBase claimInsertBase = ClaimInsertBase.builder()
                .opClmInfoList(createInsertOpClmInfoList(claimRequestVO, claimDefine))
                .opOrdBnfRelInfoList(createInsertOpOrdBnfRelInfoList(claimRequestVO, claimDefine))
                .build();

        return setUpClmNo(claimInsertBase, claimDefine);
    }

    private List<OpClmInfo> createInsertOpClmInfoList(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine){
        List<OpClmInsertCreator> creators = OpClmInsertCreator.getCreators(claimDefine);
        List<OpClmInfo> opClmInfoList = new ArrayList<>();

        for (ClaimGoodsInfo claimGoodsInfo : claimRequestVO.getClaimGoodsInfoList()) {
            for (int i=0; i < creators.size(); i++) {
                OpClmInsertCreator creator = creators.get(i);
                OpClmInfo opClmInfo = creator.create(claimGoodsInfo);
                opClmInfo.setProcSeq(opClmInfo.getProcSeq() + (i + 1));
                opClmInfoList.add(opClmInfo);
            }
        }

        return opClmInfoList;
    }

    private List<OpOrdBnfRelInfo> createInsertOpOrdBnfRelInfoList(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine){
        List<OrderBenefitBaseVO> orderBenefitBaseVOList = claimRequestVO.getOrderBenefitBaseVOList();
        List<OpBnfRelInsertCreator> creators = OpBnfRelInsertCreator.getCreators(claimDefine);

        return ClaimCreator.create(orderBenefitBaseVOList, creators);
    }

    private ClaimInsertBase setUpClmNo(ClaimInsertBase claimInsertBase, ClaimDefine claimDefine){
        ClaimNumberCreator claimNumberCreator = ClaimNumberCreator.of();
        if(!claimNumberCreator.hasClaimDefine(claimDefine)) return claimInsertBase;

        String claimNo = claimNumberCreator.create(claimMapper::getClaimNo);
        claimInsertBase.setClmNo(claimNo);
        return claimInsertBase;
    }

    public ClaimUpdateBase createClaimUpdateBase(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine){
        return ClaimUpdateBase.builder()
                .opClmInfoList(createUpdateOpClmInfoList(claimRequestVO, claimDefine))
                .opOrdCostInfoList(createUpdateOpCostList(claimRequestVO, claimDefine))
                .opOrdBnfInfoList(createOpOrdBnfInoList(claimRequestVO, claimDefine))
                .build();
    }

    private List<OpClmInfo> createUpdateOpClmInfoList(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine){
        List<ClaimGoodsInfo> list = new ArrayList<>(claimRequestVO.getClaimGoodsInfoList());

        //철회일 경우 원 주문까지 update 해야 하기에 원 주문 조회
        if(claimDefine == ClaimDefine.GEW || claimDefine == ClaimDefine.GRW){
            List<ClaimGoodsInfo> orgOpClmList = claimMapper.getOrgOpClmList(claimRequestVO.getClaimGoodsInfoList());
            list.addAll(orgOpClmList);
        }

        List<OpClmUpdateCreator> creators = OpClmUpdateCreator.getCreators(claimDefine);
        return ClaimCreator.create(list, creators);
    }

    private List<OpOrdBnfInfo> createOpOrdBnfInoList(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine){
        List<OrderBenefitBaseVO> orderBenefitBaseVOList = claimRequestVO.getOrderBenefitBaseVOList();
        List<OpBnfUpdateCreator> creators = OpBnfUpdateCreator.getCreators(claimDefine);
        return ClaimCreator.create(orderBenefitBaseVOList, creators);
    }

    private List<OpOrdCostInfo> createUpdateOpCostList(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine){
        List<ClaimDeliveryInfo> claimDeliveryInfoList = claimRequestVO.getClaimDeliveryInfoList();
        List<OpCostUpdateCreator> creators = OpCostUpdateCreator.getCreators(claimDefine);

        return ClaimCreator.create(claimDeliveryInfoList, creators);
    }


}
