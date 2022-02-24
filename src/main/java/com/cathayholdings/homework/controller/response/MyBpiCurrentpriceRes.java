package com.cathayholdings.homework.controller.response;

import java.util.Map;

public class MyBpiCurrentpriceRes {

    private String updatedISO;

    private Map<String, Rate> rateList;

    public MyBpiCurrentpriceRes() {
    }

    private MyBpiCurrentpriceRes(String updatedISO, Map<String, Rate> rateList) {
        this.updatedISO = updatedISO;
        this.rateList = rateList;
    }

    public String getUpdatedISO() {
        return updatedISO;
    }

    public void setUpdatedISO(String updatedISO) {
        this.updatedISO = updatedISO;
    }

    public Map<String, Rate> getRateList() {
        return rateList;
    }

    public void setRateList(Map<String, Rate> rateList) {
        this.rateList = rateList;
    }

    public static MyBpiCurrentpriceRes createInstance(String updatedISO, Map<String, Rate> rateList) {
        return new MyBpiCurrentpriceRes(updatedISO, rateList);
    }

    public static class Rate {

        private String code;

        private String name;

        private String rate;

        public Rate() {
        }

        private Rate(String code, String name, String rate) {
            this.code = code;
            this.name = name;
            this.rate = rate;
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

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public static Rate createInstance(String code, String name, String rate) {
            return new Rate(code, name, rate);
        }
    }
}
