package com.plateer.ec1.payment.controller;

import com.google.gson.Gson;
import com.plateer.ec1.payment.processor.impl.InicisProcessor;
import com.plateer.ec1.payment.utils.InicisApiConstants;
import com.plateer.ec1.payment.vo.res.VacctDpstCmtResVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
public class InicisController {

    private final InicisProcessor inicisProcessor;

    @PostMapping("/payment/vcaatDeposit")
    public void completeVacctDeposit(VacctDpstCmtResVO data, HttpServletRequest req){
        String clientIp  = StringUtils.isEmpty(req.getHeader("X-Forwarded-For")) ?  req.getRemoteAddr() : req.getHeader("X-Forwarded-For");
        if(!InicisApiConstants.VACCT_DPST_NTC_LIST.contains(clientIp)) return;

        inicisProcessor.completeVacctDeposit(data);
    }
}
