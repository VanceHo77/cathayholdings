package com.cathayholdings.homework.controller;

import com.cathayholdings.homework.controller.request.CurrencyCodeReq;
import com.cathayholdings.homework.controller.request.CurrencyReq;
import com.cathayholdings.homework.controller.response.CurrencyRes;
import com.cathayholdings.homework.service.CurrencyManagementService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 實作內容：1. 幣別DB維護功能
 */
@RestController
public class CurrencyManagementController {

    private final CurrencyManagementService service;

    public CurrencyManagementController(CurrencyManagementService service) {
        this.service = service;
    }

    /**
     * 查詢
     */
    @GetMapping(value = "/currencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyRes getCurrencies() {
        List<CurrencyRes.CurrencyInfo> res = service.getCurrencies();
        return CurrencyRes.valueOf(res);
    }

    /**
     * 新增
     */
    @PostMapping(value = "/currency", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createCurrency(@Valid @RequestBody CurrencyReq req) {
        service.createCurrency(req);
    }

    /**
     * 修改
     */
    @PatchMapping(value = "/currency", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateCurrency(@Valid @RequestBody CurrencyReq req) {
        service.updateCurrency(req);
    }

    /**
     * 刪除
     */
    @DeleteMapping(value = "/currency", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteCurrency(@Valid @RequestBody CurrencyCodeReq req) {
        service.deleteCurrency(req);
    }
}
