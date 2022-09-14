package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.creator.insert.bnfrel.OpBnfRelInsertCreator;
import com.plateer.ec1.claim.creator.insert.opclm.OpClmInsertCreator;
import com.plateer.ec1.claim.creator.insert.opcost.OpCostInsertCreator;
import com.plateer.ec1.claim.creator.update.bnf.OpBnfInfoUpdateCreator;
import com.plateer.ec1.claim.creator.update.opclm.OpClmUpdateCreator;
import com.plateer.ec1.claim.creator.update.opcost.OpCostUpdateCreator;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ClaimCreatorVO {

    private List<OpClmInsertCreator> opClmInsertCreators;
    private List<OpCostInsertCreator> opCostInsertCreators;
    private List<OpBnfRelInsertCreator> opBnfRelInsertCreators;
    private List<OpClmUpdateCreator> opClmUpdateCreators;
    private List<OpCostUpdateCreator> opCostUpdateCreators;
    private List<OpBnfInfoUpdateCreator> opBnfInfoUpdateCreators;

}
