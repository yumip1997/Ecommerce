package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.vo.ClaimBaseVO;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ClaimDataCreator {

    public static OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> createOrdClmCreationVO(ClaimRequestVO claimRequestVO, Supplier<String> clmNoSupplier){
        ClaimDefine claimDefine = ClaimDefine.of(claimRequestVO);
        return OrdClmCreationVO.<ClaimInsertBase, ClaimUpdateBase>builder()
                .insertData(createClaimInsertBase(claimRequestVO, claimDefine, clmNoSupplier))
                .updateData(createClaimUpdateBase(claimRequestVO))
                .build();
    }

    public static ClaimInsertBase createClaimInsertBase(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine, Supplier<String> clmNoSupplier) {
        List<OpClmInfo> opClmInfoList = claimRequestVO.toInsertOpClmInfoList(claimDefine, clmNoSupplier);
        return ClaimInsertBase.builder().opClmInfoList(opClmInfoList).build();
    }

    public static ClaimUpdateBase createClaimUpdateBase(ClaimRequestVO claimRequestVO){
        return new ClaimUpdateBase();
    }


}
