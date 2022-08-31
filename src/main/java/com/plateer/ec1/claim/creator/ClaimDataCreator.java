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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ClaimDataCreator {

    private final ClaimMapper claimMapper;

    public static ClaimDataCreator of(ClaimMapper claimMapper){
        return new ClaimDataCreator(claimMapper);
    }

    //TODO FACTORY에서 꺼내오는 거로 변경 예정
    public OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> createOrdClmCreationVO(ClaimRequestVO claimRequestVO, ClaimView claimView){
        ClaimBusiness claimBusiness = ClaimBusiness.of(claimRequestVO);
        return OrdClmCreationVO.<ClaimInsertBase, ClaimUpdateBase>builder()
                .insertData(createClaimInsertBase(claimView.clone(), claimBusiness))
                .updateData(createClaimUpdateBase(claimView.clone(), claimBusiness))
                .build();
    }

    public ClaimInsertBase createClaimInsertBase(ClaimView claimView, ClaimBusiness claimBusiness) {
        ClaimInsertBase claimInsertBase = ClaimInsertBase.builder()
                .opClmInfoList(createInsertOpClmInfoList(claimView, claimBusiness))
                .opOrdCostInfoList(creatInsertOpOrdCostInfo(claimView, claimBusiness))
                .opOrdBnfRelInfoList(createInsertOpOrdBnfRelInfoList(claimView, claimBusiness))
                .build();

        return setUpClmNo(claimInsertBase, claimBusiness);
    }

    private List<OpClmInfo> createInsertOpClmInfoList(ClaimView claimView, ClaimBusiness claimBusiness){
        List<OpClmInsertCreator> creators = OpClmInsertCreator.getCreators(claimBusiness);
        List<OpClmInfo> opClmInfoList = new ArrayList<>();

        for (ClaimGoodsInfo claimGoodsInfo : claimView.getClaimGoodsInfoList()) {
            for (int i=0; i < creators.size(); i++) {
                OpClmInsertCreator creator = creators.get(i);
                OpClmInfo opClmInfo = creator.create(claimGoodsInfo);
                opClmInfo.setProcSeq(opClmInfo.getProcSeq() + (i + 1));
                opClmInfoList.add(opClmInfo);
            }
        }

        return opClmInfoList;
    }
    
    private List<OpOrdCostInfo> creatInsertOpOrdCostInfo(ClaimView claimView, ClaimBusiness claimBusiness){
        List<ClaimDeliveryInfo> claimDeliveryInfoList = claimView.getClaimDeliveryInfoList();
        if(CollectionUtils.isEmpty(claimDeliveryInfoList)) return Collections.emptyList();

        List<OpCostInsertCreator> creators = OpCostInsertCreator.getCreators(claimBusiness);
        return ClaimCreator.create(claimView, creators);
    }

    private List<OpOrdBnfRelInfo> createInsertOpOrdBnfRelInfoList(ClaimView claimView, ClaimBusiness claimBusiness){
        List<OrderBenefitBaseVO> orderBenefitBaseVOList = claimView.getOrderBenefitBaseVOList();
        if(CollectionUtils.isEmpty(orderBenefitBaseVOList)) return Collections.emptyList();
        
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

    public ClaimUpdateBase createClaimUpdateBase(ClaimView claimView, ClaimBusiness claimBusiness){
        return ClaimUpdateBase.builder()
                .opClmInfoList(createUpdateOpClmInfoList(claimView, claimBusiness))
                .opOrdCostInfoList(createUpdateOpCostList(claimView, claimBusiness))
                .opOrdBnfInfoList(createUpdateOpOrdBnfInfoList(claimView, claimBusiness))
                .build();
    }

    private List<OpClmInfo> createUpdateOpClmInfoList(ClaimView claimView, ClaimBusiness claimBusiness){
        List<ClaimGoodsInfo> list = new ArrayList<>(claimView.getClaimGoodsInfoList());

        //철회일 경우 원 주문까지 update 해야 하기에 원 주문 조회
        if(claimBusiness == ClaimBusiness.GEW || claimBusiness == ClaimBusiness.GRW){
            List<ClaimGoodsInfo> orgOpClmList = claimMapper.getOrgOpClmList(claimView.getClaimGoodsInfoList());
            list.addAll(orgOpClmList);
        }

        List<OpClmUpdateCreator> creators = OpClmUpdateCreator.getCreators(claimBusiness);
        return ClaimCreator.create(list, creators);
    }

    private List<OpOrdBnfInfo> createUpdateOpOrdBnfInfoList(ClaimView claimView, ClaimBusiness claimBusiness){
        List<OrderBenefitBaseVO> orderBenefitBaseVOList = claimView.getOrderBenefitBaseVOList();
        if(CollectionUtils.isEmpty(orderBenefitBaseVOList)) return Collections.emptyList();

        List<OpBnfUpdateCreator> creators = OpBnfUpdateCreator.getCreators(claimBusiness);
        return ClaimCreator.create(orderBenefitBaseVOList, creators);
    }

    private List<OpOrdCostInfo> createUpdateOpCostList(ClaimView claimView, ClaimBusiness claimBusiness){
        List<ClaimDeliveryInfo> claimDeliveryInfoList = claimView.getClaimDeliveryInfoList();
        if(CollectionUtils.isEmpty(claimDeliveryInfoList)) return Collections.emptyList();

        List<OpCostUpdateCreator> creators = OpCostUpdateCreator.getCreators(claimBusiness);
        return ClaimCreator.create(claimDeliveryInfoList, creators);
    }


}
