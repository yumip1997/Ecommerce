package com.plateer.ec1.promotion.download.factory;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.promotion.download.validator.CupDownloadValidator;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CupDownloadValidatorFactory extends FactoryTemplate<PRM0009Code, CupDownloadValidator> {

    public CupDownloadValidatorFactory(List<CupDownloadValidator> list) { super(list);}

}
