package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.define.ClaimDefine;

import java.util.List;

//TODO 삭제예정
public interface ClaimGroups<T,U> {

    List<ClaimDefine> groupingClaim();

    T create(ClaimDefine claimDefine, U u);
}
