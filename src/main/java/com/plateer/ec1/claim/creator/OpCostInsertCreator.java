package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimDeliveryInfo;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

@RequiredArgsConstructor
public enum OpCostInsertCreator implements ClaimCreator<List<OpOrdCostInfo>, ClaimRequestVO>{

    CNL_CREATOR(Arrays.asList(GCC, MCA)){
        @Override
        public List<OpOrdCostInfo> create(ClaimRequestVO claimRequestVO) {
            return claimRequestVO.getClaimDeliveryInfoList().stream()
                    .map(ClaimDeliveryInfo::toOpOrdCostInfoOfCancel)
                    .collect(Collectors.toList());
        }
    },
    EX_APT_CREATOR(Collections.singletonList(GEA)){
        @Override
        public List<OpOrdCostInfo> create(ClaimRequestVO req) {
            return req.getClaimDeliveryInfoList().stream()
                    .map(e -> {
                        int dvGrpNo = e.getDvGrpNoOfCreatedClm(req.getMapByOrdNoDvGrpNo(), req.getMapByOrdNoOrdSeqOrgProcSeq());
                        return e.toOpOrdCostInfoExchange(dvGrpNo);
                    }).collect(Collectors.toList());
        }
    },
    RETURN_APT_CREATOR(Collections.singletonList(GRA)){
        @Override
        public List<OpOrdCostInfo> create(ClaimRequestVO req) {
            List<OpOrdCostInfo> opOrdCostInfoList = new ArrayList<>();

            List<ClaimDeliveryInfo> claimDeliveryInfoList = req.getClaimDeliveryInfoList();
            for (ClaimDeliveryInfo claimDeliveryInfo : claimDeliveryInfoList) {
                int dvGrpNo = claimDeliveryInfo.getDvGrpNoOfCreatedClm(req.getMapByOrdNoDvGrpNo(), req.getMapByOrdNoOrdSeqOrgProcSeq());

                if(claimDeliveryInfo.isCompanyImtnRsnCcd()){
                    List<OpOrdCostInfo> companyOpOrdCostInfoList = claimDeliveryInfo.toOpOrdCostInfoOfReturnCompany(dvGrpNo);
                    opOrdCostInfoList.addAll(companyOpOrdCostInfoList);
                    continue;
                }

                //고객사유 착불(환불금액 < 반품비)
                if(claimDeliveryInfo.isCustomerDeliveryPaid()){
                    OpOrdCostInfo opOrdCostInfo = claimDeliveryInfo.toOpOrdCostInfoOfReturnCustomerPayOnDelivery(dvGrpNo);
                    opOrdCostInfoList.add(opOrdCostInfo);
                    continue;
                }

                //고객사유 착불x(환불금액 >= 반품비)
                if(claimDeliveryInfo.isCustomerNotDeliveryPaid()){
                    OpOrdCostInfo opOrdCostInfo = claimDeliveryInfo.toOpOrdCostInfoOfReturnCustomerNotPayOnDelivery(dvGrpNo);
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
