package com.cathayholdings.homework.controller.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CurrencyCodeReq {

    @NotBlank
    @Pattern(regexp = "^([A-Z]{3})$")
    private String code;

    public CurrencyCodeReq() {
    }

    public CurrencyCodeReq(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
