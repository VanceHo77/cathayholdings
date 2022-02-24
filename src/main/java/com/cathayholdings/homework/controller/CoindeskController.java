package com.cathayholdings.homework.controller;

import com.cathayholdings.homework.controller.response.MyBpiCurrentpriceRes;
import com.cathayholdings.homework.service.CoindeskService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doindesk")
public class CoindeskController {

    private final CoindeskService service;

    public CoindeskController(CoindeskService service) {
        this.service = service;
    }

    /**
     * 實作內容：2. 呼叫coindesk的API
     */
    @GetMapping(value = "/bpi/currentprice", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCurrentprice() {
        return service.getCurrentprice();
    }

    /**
     * 實作內容：3. 呼叫 coindesk 的 API，並進行資料轉換，組成新 API。
     */
    @GetMapping(value = "/my/bpi/currentprice", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyBpiCurrentpriceRes transferCurrentprice() {
        return service.transferCurrentprice();
    }
}
