package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ClaimDataCreator {

    private final ClaimMapper claimMapper;

    public static ClaimDataCreator of(ClaimMapper claimMapper){
        return new ClaimDataCreator(claimMapper);
    }

    public OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> createOrdClmCreationVO(ClaimRequestVO claimRequestVO){
        ClaimBusiness claimBusiness = ClaimBusiness.of(claimRequestVO);
        return OrdClmCreationVO.<ClaimInsertBase, ClaimUpdateBase>builder()
                .insertData(createClaimInsertBase(claimRequestVO.clone(), claimBusiness))
                .updateData(createClaimUpdateBase(claimRequestVO.clone(), claimBusiness))
                .build();
    }

    public ClaimInsertBase createClaimInsertBase(ClaimRequestVO claimRequestVO, ClaimBusiness claimBusiness) {
        ClaimInsertBase claimInsertBase = ClaimInsertBase.builder()
                .opClmInfoList(createInsertOpClmInfoList(claimRequestVO, claimBusiness))
                .opOrdBnfRelInfoList(createInsertOpOrdBnfRelInfoList(claimRequestVO, claimBusiness))
                .build();

        return setUpClmNo(claimInsertBase, claimBusiness);
    }

    private List<OpClmInfo> createInsertOpClmInfoList(ClaimRequestVO claimRequestVO, ClaimBusiness claimBusiness){
        List<OpClmInsertCreator> creators = OpClmInsertCreator.getCreators(claimBusiness);
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

    private List<OpOrdBnfRelInfo> createInsertOpOrdBnfRelInfoList(ClaimRequestVO claimRequestVO, ClaimBusiness claimBusiness){
        List<OrderBenefitBaseVO> orderBenefitBaseVOList = claimRequestVO.getOrderBenefitBaseVOList();
        List<OpBnfRelInsertCreator> creators = OpBnfRelInsertCreator.getCreators(claimBusiness);

        return ClaimCreator.create(orderBenefitBaseVOList, creators);
    }

    private ClaimInsertBase setUpClmNo(ClaimInsertBase claimInsertBase, ClaimBusiness claimBusiness){
        ClaimNumberCreator claimNumberCreator = ClaimNumberCreator.of();
        if(!claimNumberCreator.hasClaimDefine(claimBusiness)) return claimInsertBase;

        String claimNo = claimNumberCreator.create(claimMapper::getClaimNo);
        claimInsertBase.setClmNo(claimNo);
        return claimInsertBase;
    }

    public ClaimUpdateBase createClaimUpdateBase(ClaimRequestVO claimRequestVO, ClaimBusiness claimBusiness){
        return ClaimUpdateBase.builder()
                .opClmInfoList(createUpdateOpClmInfoList(claimRequestVO, claimBusiness))
                .opOrdCostInfoList(createUpdateOpCostList(claimRequestVO, claimBusiness))
                .opOrdBnfInfoList(createOpOrdBnfInoList(claimRequestVO, claimBusiness))
                .build();
    }

    private List<OpClmInfo> createUpdateOpClmInfoList(ClaimRequestVO claimRequestVO, ClaimBusiness claimBusiness){
        List<ClaimGoodsInfo> list = new ArrayList<>(claimRequestVO.getClaimGoodsInfoList());

        //철회일 경우 원 주문까지 update 해야 하기에 원 주문 조회
        if(claimBusiness == ClaimBusiness.GEW || claimBusiness == ClaimBusiness.GRW){
            List<ClaimGoodsInfo> orgOpClmList = claimMapper.getOrgOpClmList(claimRequestVO.getClaimGoodsInfoList());
            list.addAll(orgOpClmList);
        }

        List<OpClmUpdateCreator> creators = OpClmUpdateCreator.getCreators(claimBusiness);
        return ClaimCreator.create(list, creators);
    }

    private List<OpOrdBnfInfo> createOpOrdBnfInoList(ClaimRequestVO claimRequestVO, ClaimBusiness claimBusiness){
        List<OrderBenefitBaseVO> orderBenefitBaseVOList = claimRequestVO.getOrderBenefitBaseVOList();
        List<OpBnfUpdateCreator> creators = OpBnfUpdateCreator.getCreators(claimBusiness);
        return ClaimCreator.create(orderBenefitBaseVOList, creators);
    }

    private List<OpOrdCostInfo> createUpdateOpCostList(ClaimRequestVO claimRequestVO, ClaimBusiness claimBusiness){
        List<ClaimDeliveryInfo> claimDeliveryInfoList = claimRequestVO.getClaimDeliveryInfoList();
        List<OpCostUpdateCreator> creators = OpCostUpdateCreator.getCreators(claimBusiness);

        return ClaimCreator.create(claimDeliveryInfoList, creators);
    }


}
