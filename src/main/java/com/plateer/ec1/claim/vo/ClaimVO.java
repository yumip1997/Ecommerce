package com.plateer.ec1.claim.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClaimVO implements Cloneable{
    List<ClaimBaseVO> claimBaseVOList;
}
