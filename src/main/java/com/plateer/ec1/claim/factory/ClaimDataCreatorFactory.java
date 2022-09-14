package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.creator.insert.bnfrel.OpBnfRelInsertCreator;
import com.plateer.ec1.claim.creator.insert.opclm.OpClmInsertCreator;
import com.plateer.ec1.claim.creator.insert.opclm.impl.OpClmInsertCreatorImpl;
import com.plateer.ec1.claim.creator.insert.opcost.OpCostInsertCreator;
import com.plateer.ec1.claim.creator.update.bnf.OpBnfInfoUpdateCreator;
import com.plateer.ec1.claim.creator.update.opclm.OpClmUpdateCreator;
import com.plateer.ec1.claim.creator.update.opcost.OpCostUpdateCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimCreatorVO;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

@Component
@Getter
public class ClaimDataCreatorFactory {

    private final MultiValueMap<ClaimBusiness, OpClmInsertCreator> clmInsertCreatorMap = new LinkedMultiValueMap<>();
    private final MultiValueMap<ClaimBusiness, OpCostInsertCreator> costInsertCreatorMap = new LinkedMultiValueMap<>();
    private final MultiValueMap<ClaimBusiness, OpBnfRelInsertCreator> bnfRelInsertCreatorMap = new LinkedMultiValueMap<>();
    private final MultiValueMap<ClaimBusiness, OpClmUpdateCreator> clmUpdateCreatorMap = new LinkedMultiValueMap<>();
    private final MultiValueMap<ClaimBusiness, OpCostUpdateCreator> costUpdateCreatorMap = new LinkedMultiValueMap<>();
    private final MultiValueMap<ClaimBusiness, OpBnfInfoUpdateCreator> bnfInfoUpdateCreatorMap = new LinkedMultiValueMap<>();

    public ClaimDataCreatorFactory(List<OpCostInsertCreator> opCostInsertCreators
            , List<OpBnfRelInsertCreator> opBnfRelInsertCreators
            , List<OpClmUpdateCreator> opClmUpdateCreators
            , List<OpCostUpdateCreator> opCostUpdateCreators
            , List<OpBnfInfoUpdateCreator> opBnfInfoUpdateCreators) {
        setUpMap(clmInsertCreatorMap, Arrays.asList(OpClmInsertCreatorImpl.values()));
        setUpMap(costInsertCreatorMap, opCostInsertCreators);
        setUpMap(bnfRelInsertCreatorMap, opBnfRelInsertCreators);
        setUpMap(clmUpdateCreatorMap, opClmUpdateCreators);
        setUpMap(costUpdateCreatorMap, opCostUpdateCreators);
        setUpMap(bnfInfoUpdateCreatorMap, opBnfInfoUpdateCreators);
    }


    private <T extends ClaimDataCreator<?>> void setUpMap(MultiValueMap<ClaimBusiness, T> map, List<T> creators){
        for (T creator : creators) {
            List<ClaimBusiness> types = creator.getTypes();
            for (ClaimBusiness type : types) {
                map.add(type, creator);
            }
        }
    }

    public ClaimCreatorVO getCreators(ClaimBusiness claimBusiness){
        return ClaimCreatorVO.builder()
                .opClmInsertCreators(getClmInsertCreatorMap().get(claimBusiness))
                .opCostInsertCreators(getCostInsertCreatorMap().get(claimBusiness))
                .opBnfRelInsertCreators(getBnfRelInsertCreatorMap().get(claimBusiness))
                .opClmUpdateCreators(getClmUpdateCreatorMap().get(claimBusiness))
                .opCostUpdateCreators(getCostUpdateCreatorMap().get(claimBusiness))
                .opBnfInfoUpdateCreators(getBnfInfoUpdateCreatorMap().get(claimBusiness))
                .build();
    }

}
