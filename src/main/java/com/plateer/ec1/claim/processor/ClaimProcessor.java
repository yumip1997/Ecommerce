package com.plateer.ec1.claim.processor;

import com.plateer.ec1.claim.enums.ClaimDefine;
import com.plateer.ec1.claim.enums.ClaimProcessorType;
import com.plateer.ec1.claim.enums.ClaimValidatorType;
import com.plateer.ec1.claim.factory.ClaimValidatorFactory;
import com.plateer.ec1.claim.helper.ClaimDataCreator;
import com.plateer.ec1.claim.helper.ClaimDataManipulateHelper;
import com.plateer.ec1.claim.helper.MonitoringLogHelper;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimVO;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.claim.vo.LogVO;
import com.plateer.ec1.common.factory.CustomFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public abstract class ClaimProcessor implements CustomFactory<ClaimProcessorType> {

   private final ClaimValidatorFactory claimValidatorFactory;
   protected final MonitoringLogHelper monitoringLogHelper;
   protected final ClaimDataManipulateHelper claimDataManipulateHelper;

   public abstract void doProcess(ClaimVO claimVO);

   protected void setUpClaimNum(ClaimVO claimVO) throws Exception {
      log.info("클레임 채번 로직을 진행한다.");

      boolean flag = ClaimDefine.findNumFlag(claimVO);
      if(!flag) return;

      String claimNum = getClaimNumber();
      claimVO.setClaimNo(claimNum);
   }

   private String getClaimNumber(){
      return "";
   }

   protected void doValidationProcess(ClaimVO claimVO) throws Exception{
      ClaimValidatorType claimValidatorType = ClaimDefine.findClaimValidatorType(claimVO);
      ClaimValidator claimValidator = claimValidatorFactory.get(claimValidatorType);

      claimValidator.isValid(claimVO);
   }

   protected LogVO doClaimDataManipulationProcess(ClaimVO claimVO){
      ClaimInsertBase claimInsertBase = ClaimDataCreator.makeClaimInsertBase(claimVO);
      ClaimUpdateBase claimUpdateBase = ClaimDataCreator.makeClaimUpadateBase(claimVO);

      insertClaim(claimInsertBase);
      updateClaim(claimUpdateBase);

      return LogVO.<ClaimInsertBase, ClaimUpdateBase>builder()
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

   protected void verifyAmount(ClaimVO claimVO) throws Exception {
      ClaimValidatorType claimValidatorType = ClaimDefine.findClaimValidatorType(claimVO);
      ClaimValidator claimValidator = claimValidatorFactory.get(claimValidatorType);

      claimValidator.verifyAmount(claimVO);
   }

}
