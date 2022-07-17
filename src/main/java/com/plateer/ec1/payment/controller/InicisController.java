package com.plateer.ec1.payment.controller;

import com.plateer.ec1.common.enums.CommonConstants;
import com.plateer.ec1.common.utils.HttpServletRequestUtil;
import com.plateer.ec1.payment.processor.impl.InicisProcessor;
import com.plateer.ec1.payment.utils.inicis.InicisApiConstants;
import com.plateer.ec1.payment.vo.inicis.res.VacctDpstCmtResVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class InicisController {

    private final InicisProcessor inicisProcessor;

    @PostMapping("/vcaatDeposit")
    public String completeVacctDeposit(VacctDpstCmtResVO data, HttpServletRequest req){
        String clientIp = HttpServletRequestUtil.getClientIP(req);
        if(!InicisApiConstants.VACCT_DPST_NTC_LIST.contains(clientIp)) return CommonConstants.FAIL.getCode();

        return inicisProcessor.completeVacctDeposit(data);
    }
}
