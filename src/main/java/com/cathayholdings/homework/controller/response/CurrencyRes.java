package com.cathayholdings.homework.controller.response;

import com.cathayholdings.homework.model.entity.Currency;

import java.util.List;

public class CurrencyRes {

    private List<CurrencyInfo> currencyInfoList;

    public CurrencyRes() {
    }

    private CurrencyRes(List<CurrencyInfo> currencyInfoList) {
        this.currencyInfoList = currencyInfoList;
    }

    public List<CurrencyInfo> getCurrencyInfoList() {
        return currencyInfoList;
    }

    public void setCurrencyInfoList(List<CurrencyInfo> currencyInfoList) {
        this.currencyInfoList = currencyInfoList;
    }

    public static CurrencyRes valueOf(List<CurrencyInfo> currencyInfoList) {
        return new CurrencyRes(currencyInfoList);
    }

    public static class CurrencyInfo {
        private String code;

        private String name;

        public CurrencyInfo() {
        }

        public CurrencyInfo(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static CurrencyInfo valueOf(Currency entity) {
            return new CurrencyInfo(entity.getCode(), entity.getName());
        }
    }
}
