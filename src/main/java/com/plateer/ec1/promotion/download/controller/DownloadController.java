package com.plateer.ec1.promotion.download.controller;

import com.plateer.ec1.promotion.download.service.DownloadService;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DownloadController {

    private final DownloadService downloadService;

    @PostMapping
    public void downloadCup(@RequestBody CupDwlRequestVO cupDwlRequestVO){
        downloadService.download(cupDwlRequestVO);
    }

}
