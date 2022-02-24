package com.cathayholdings.homework.controller.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CurrencyReq {

    @NotBlank
    @Pattern(regexp = "^([A-Z]{3})$")
    private String code;

    @NotBlank
    @Length(max = 30)
    private String name;

    public CurrencyReq() {
    }

    public CurrencyReq(String code, String name) {
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
}
