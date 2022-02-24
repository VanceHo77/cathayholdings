package com.cathayholdings.homework.service;

import com.cathayholdings.homework.controller.response.MyBpiCurrentpriceRes;
import com.cathayholdings.homework.dto.BpiCurrentpriceDTO;
import com.cathayholdings.homework.dto.CurrencyRate;
import com.cathayholdings.homework.manager.HttpConnectManager;
import com.cathayholdings.homework.model.entity.Currency;
import com.cathayholdings.homework.model.respository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CoindeskService {

    private final String CURRENTPRICE_API = "https://api.coindesk.com/v1/bpi/currentprice.json";

    private final ZoneId utc = TimeZone.getTimeZone("UTC").toZoneId();

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").withZone(utc);

    private final HttpConnectManager connectManager;

    private final CurrencyRepository currencyRepository;


    public CoindeskService(HttpConnectManager connectManager, CurrencyRepository currencyRepository) {
        this.connectManager = connectManager;
        this.currencyRepository = currencyRepository;
    }

    public String getCurrentprice() {
        return connectManager.get(CURRENTPRICE_API);
    }

    public MyBpiCurrentpriceRes transferCurrentprice() {
        BpiCurrentpriceDTO resDto = connectManager.get(CURRENTPRICE_API, BpiCurrentpriceDTO.class);

        TemporalAccessor temporalAccessor = DateTimeFormatter.ISO_DATE_TIME.parse(resDto.getTime().getUpdatedISO());
        Instant instant = Instant.from(temporalAccessor);
        String updatedISOText = dateTimeFormatter.format(instant);

        Map<String, Currency> currencyCodeNameMap =
                currencyRepository.findAll().stream().collect(Collectors.toMap(Currency::getCode, Function.identity()));

        Map<String, CurrencyRate> bpiRateMap = resDto.getBpi();
        Map<String, MyBpiCurrentpriceRes.Rate> rateMap = new LinkedHashMap<>(bpiRateMap.size());
        for (Map.Entry<String, CurrencyRate> entry : bpiRateMap.entrySet()) {
            String currency = entry.getKey();
            CurrencyRate cr = entry.getValue();
            String currencyName =
                    currencyCodeNameMap.containsKey(cr.getCode())
                            ? currencyCodeNameMap.get(cr.getCode()).getName()
                            : "Undefined";
            rateMap.put(currency, MyBpiCurrentpriceRes.Rate.createInstance(cr.getCode(), currencyName, cr.getRate()));
        }

        return MyBpiCurrentpriceRes.createInstance(updatedISOText, rateMap);
    }

}
