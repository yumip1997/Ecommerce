package com.plateer.ec1.claim.processor;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.enums.ProcessorType;
import com.plateer.ec1.claim.enums.ValidatorType;
import com.plateer.ec1.claim.factory.ClaimValidatorFactory;
import com.plateer.ec1.claim.helper.ClaimDataCreator;
import com.plateer.ec1.claim.helper.ClaimDataManipulateHelper;
import com.plateer.ec1.claim.helper.MonitoringLogHelper;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimBaseVO;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.claim.vo.LogVO;
import com.plateer.ec1.common.factory.CustomFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public abstract class ClaimProcessor implements CustomFactory<ProcessorType> {

   private final ClaimValidatorFactory claimValidatorFactory;
   protected final MonitoringLogHelper monitoringLogHelper;
   protected final ClaimDataManipulateHelper claimDataManipulateHelper;

   public abstract void doProcess(ClaimBaseVO claimBaseVO);

   protected void setUpClaimNum(ClaimBaseVO claimBaseVO) throws Exception {
      log.info("클레임 채번 로직을 진행한다.");

      boolean flag = ClaimDefine.findNumFlag(claimBaseVO);
      if(!flag) return;

      String claimNum = getClaimNumber();
      claimBaseVO.setClmNo(claimNum);
   }

   private String getClaimNumber(){
      return "";
   }

   protected void doValidationProcess(ClaimBaseVO claimBaseVO) throws Exception{
      ValidatorType validatorType = ClaimDefine.findClaimValidatorType(claimBaseVO);
      ClaimValidator claimValidator = claimValidatorFactory.get(validatorType);

      claimValidator.isValid(claimBaseVO);
   }

   protected LogVO doClaimDataManipulationProcess(ClaimBaseVO claimBaseVO){
      ClaimInsertBase claimInsertBase = ClaimDataCreator.makeClaimInsertBase(claimBaseVO);
      ClaimUpdateBase claimUpdateBase = ClaimDataCreator.makeClaimUpadateBase(claimBaseVO);

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

   protected void verifyAmount(ClaimBaseVO claimBaseVO) throws Exception {
      ValidatorType validatorType = ClaimDefine.findClaimValidatorType(claimBaseVO);
      ClaimValidator claimValidator = claimValidatorFactory.get(validatorType);

      claimValidator.verifyAmount(claimBaseVO);
   }

}
