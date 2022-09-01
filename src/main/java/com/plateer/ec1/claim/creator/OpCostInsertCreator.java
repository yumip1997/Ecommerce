package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimDeliveryCostInfo;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

@RequiredArgsConstructor
public enum OpCostInsertCreator implements ClaimCreator<List<OpOrdCostInfo>, ClaimView>{

    CNL_CREATOR(Arrays.asList(GCC, MCA)){
        @Override
        public List<OpOrdCostInfo> create(ClaimView claimView) {
            return claimView.getClaimDeliveryCostInfoList().stream()
                    .map(ClaimDeliveryCostInfo::toOpOrdCostInfoOfCancel)
                    .collect(Collectors.toList());
        }
    },
    EX_APT_CREATOR(Collections.singletonList(GEA)){
        @Override
        public List<OpOrdCostInfo> create(ClaimView req) {
            return req.getClaimDeliveryCostInfoList().stream()
                    .map(e -> {
                        int dvGrpNo = e.getDvGrpNoOfCreatedClm(req.getMapByOrdNoDvGrpNo(), req.getMapByOrdNoOrdSeqOrgProcSeq());
                        return e.toOpOrdCostInfoExchange(dvGrpNo);
                    }).collect(Collectors.toList());
        }
    },
    RETURN_APT_CREATOR(Collections.singletonList(GRA)){
        @Override
        public List<OpOrdCostInfo> create(ClaimView req) {
            List<OpOrdCostInfo> opOrdCostInfoList = new ArrayList<>();

            List<ClaimDeliveryCostInfo> claimDeliveryCostInfoList = req.getClaimDeliveryCostInfoList();
            for (ClaimDeliveryCostInfo claimDeliveryCostInfo : claimDeliveryCostInfoList) {
                int dvGrpNo = claimDeliveryCostInfo.getDvGrpNoOfCreatedClm(req.getMapByOrdNoDvGrpNo(), req.getMapByOrdNoOrdSeqOrgProcSeq());

                if(claimDeliveryCostInfo.isCompanyImtnRsnCcd()){
                    List<OpOrdCostInfo> companyOpOrdCostInfoList = claimDeliveryCostInfo.toOpOrdCostInfoOfReturnCompany(dvGrpNo);
                    opOrdCostInfoList.addAll(companyOpOrdCostInfoList);
                    continue;
                }

                //고객사유 착불(환불금액 < 반품비)
                if(claimDeliveryCostInfo.isCustomerDeliveryPaid()){
                    OpOrdCostInfo opOrdCostInfo = claimDeliveryCostInfo.toOpOrdCostInfoOfReturnCustomerPayOnDelivery(dvGrpNo);
                    opOrdCostInfoList.add(opOrdCostInfo);
                    continue;
                }

                //고객사유 착불x(환불금액 >= 반품비)
                if(claimDeliveryCostInfo.isCustomerNotDeliveryPaid()){
                    OpOrdCostInfo opOrdCostInfo = claimDeliveryCostInfo.toOpOrdCostInfoOfReturnCustomerNotPayOnDelivery(dvGrpNo);
                    opOrdCostInfoList.add(opOrdCostInfo);
                }
            }

            return opOrdCostInfoList;
        }
    };


    private final List<ClaimBusiness> groups;

    @Override
    public List<ClaimBusiness> getTypes() {
        return this.groups;
    }

    public static List<OpCostInsertCreator> getCreators(ClaimBusiness claimBusiness){
        return Arrays.stream(OpCostInsertCreator.values())
                .filter(e -> e.hasClaimDefine(claimBusiness))
                .collect(Collectors.toList());
    }
}
