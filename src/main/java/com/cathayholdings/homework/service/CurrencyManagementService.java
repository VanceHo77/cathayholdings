package com.cathayholdings.homework.service;

import com.cathayholdings.homework.controller.request.CurrencyCodeReq;
import com.cathayholdings.homework.controller.request.CurrencyReq;
import com.cathayholdings.homework.controller.response.CurrencyRes;
import com.cathayholdings.homework.model.entity.Currency;
import com.cathayholdings.homework.model.respository.CurrencyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyManagementService {

    private final CurrencyRepository currencyRepository;

    public CurrencyManagementService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<CurrencyRes.CurrencyInfo> getCurrencies() {
        return currencyRepository
                .findAll()
                .stream()
                .map(CurrencyRes.CurrencyInfo::valueOf)
                .collect(Collectors.toList());
    }

    public void createCurrency(CurrencyReq req) {
        if (currencyRepository.existsByCode(req.getCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Currency entity = Currency.valueOf(req);
        currencyRepository.save(entity);
    }

    public void updateCurrency(CurrencyReq req) {
        Currency currency = findCurrencyByCode(req.getCode());

        currency.setName(req.getName());
        currency.setUpdatedTime(new Date());

        currencyRepository.save(currency);
    }

    private Currency findCurrencyByCode(String code) {
        return currencyRepository
                .findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "code: " + code + " not found"));
    }

    public void deleteCurrency(CurrencyCodeReq req) {
        Currency currency = findCurrencyByCode(req.getCode());

        currency.setDeleted(true);
        currency.setDeletedTime(new Date());

        currencyRepository.save(currency);
    }
}
