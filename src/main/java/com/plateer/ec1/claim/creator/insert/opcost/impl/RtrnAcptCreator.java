package com.plateer.ec1.claim.creator.insert.opcost.impl;

import com.plateer.ec1.claim.creator.insert.opcost.OpCostInsertCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimDeliveryCostInfo;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimBusiness.GRA;

@Component
public class RtrnAcptCreator implements OpCostInsertCreator {

    @Override
    public List<OpOrdCostInfo> create(ClaimView claimView) {
        List<OpOrdCostInfo> opOrdCostInfoList = new ArrayList<>();

        List<ClaimDeliveryCostInfo> claimDeliveryCostInfoList = claimView.getClaimDeliveryCostInfoList();
        for (ClaimDeliveryCostInfo claimDeliveryCostInfo : claimDeliveryCostInfoList) {
            if(claimDeliveryCostInfo.isCompanyImtnRsnCcd()){
                List<OpOrdCostInfo> companyOpOrdCostInfoList = claimDeliveryCostInfo.toOpOrdCostInfoOfReturnCompany();
                opOrdCostInfoList.addAll(companyOpOrdCostInfoList);
                continue;
            }

            //고객사유 착불(환불금액 < 반품비)
            if(claimDeliveryCostInfo.isCustomerDeliveryPaid()){
                OpOrdCostInfo opOrdCostInfo = claimDeliveryCostInfo.toOpOrdCostInfoOfReturnCustomerPayOnDelivery();
                opOrdCostInfoList.add(opOrdCostInfo);
                continue;
            }

            //고객사유 착불x(환불금액 >= 반품비)
            if(claimDeliveryCostInfo.isCustomerNotDeliveryPaid()){
                OpOrdCostInfo opOrdCostInfo = claimDeliveryCostInfo.toOpOrdCostInfoOfReturnCustomerNotPayOnDelivery();
                opOrdCostInfoList.add(opOrdCostInfo);
            }
        }

        return opOrdCostInfoList;
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Collections.singletonList(GRA);
    }
}
