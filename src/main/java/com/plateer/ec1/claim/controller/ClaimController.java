package com.plateer.ec1.claim.controller;

import com.plateer.ec1.claim.service.ClaimService;
import com.plateer.ec1.claim.vo.ClaimBaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping("/claim")
    public void claim(@RequestBody ClaimBaseVO claimBaseVO){
        claimService.claim(claimBaseVO);
    }
}
