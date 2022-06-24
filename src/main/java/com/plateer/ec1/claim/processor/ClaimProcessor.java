package com.plateer.ec1.claim.processor;

import com.plateer.ec1.claim.enums.ClaimDefine;
import com.plateer.ec1.claim.enums.ClaimProcessorType;
import com.plateer.ec1.claim.enums.ClaimValidatorType;
import com.plateer.ec1.claim.factory.ClaimValidatorFactory;
import com.plateer.ec1.claim.helper.ClaimDataCreator;
import com.plateer.ec1.claim.helper.ClaimDataManipulateHelper;
import com.plateer.ec1.claim.helper.MonitoringLogHelper;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimDto;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.claim.vo.LogDto;
import com.plateer.ec1.common.factory.CustomFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public abstract class ClaimProcessor implements CustomFactory<ClaimProcessorType> {

   private final ClaimValidatorFactory claimValidatorFactory;
   protected final MonitoringLogHelper monitoringLogHelper;
   protected final ClaimDataManipulateHelper claimDataManipulateHelper;

   public abstract void doProcess(ClaimDto claimDto);

   protected void setUpClaimNum(ClaimDto claimDto) throws Exception {
      log.info("클레임 채번 로직을 진행한다.");

      boolean flag = ClaimDefine.findNumFlag(claimDto);
      if(!flag) return;

      String claimNum = getClaimNumber();
      claimDto.setClaimNo(claimNum);
   }

   private String getClaimNumber(){
      return "";
   }

   protected void doValidationProcess(ClaimDto claimDto) throws Exception{
      ClaimValidatorType claimValidatorType = ClaimDefine.findClaimValidatorType(claimDto);
      ClaimValidator claimValidator = claimValidatorFactory.get(claimValidatorType);

      claimValidator.isValid(claimDto);
   }

   protected LogDto doClaimDataManipulationProcess(ClaimDto claimDto){
      ClaimInsertBase claimInsertBase = ClaimDataCreator.makeClaimInsertBase(claimDto);
      ClaimUpdateBase claimUpdateBase = ClaimDataCreator.makeClaimUpadateBase(claimDto);

      insertClaim(claimInsertBase);
      updateClaim(claimUpdateBase);

      return LogDto.<ClaimInsertBase, ClaimUpdateBase>builder()
              .insertData(claimInsertBase)
              .updateData(claimUpdateBase)
              .build();
   }

   private ClaimInsertBase insertClaim(ClaimInsertBase claimInsertBase) {
      claimDataManipulateHelper.insertClaimData(claimInsertBase);
      return claimInsertBase;
   }

   private ClaimUpdateBase updateClaim(ClaimUpdateBase claimUpdateBase) {
      claimDataManipulateHelper.updateClaimData(claimUpdateBase);
      return claimUpdateBase;
   }

   protected void verifyAmount(ClaimDto claimDto) throws Exception {
      ClaimValidatorType claimValidatorType = ClaimDefine.findClaimValidatorType(claimDto);
      ClaimValidator claimValidator = claimValidatorFactory.get(claimValidatorType);

      claimValidator.verifyAmount(claimDto);
   }

}
