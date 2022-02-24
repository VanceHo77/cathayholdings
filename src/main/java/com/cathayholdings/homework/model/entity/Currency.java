package com.cathayholdings.homework.model.entity;

import com.cathayholdings.homework.controller.request.CurrencyReq;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@DynamicUpdate
@Entity
@Where(clause = "is_deleted = 0")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private Boolean isDeleted;

    private Date createdTime;

    private Date updatedTime;

    private Date deletedTime;

    public Currency() {
    }

    public Currency(Long id, String code, String name, Boolean isDeleted, Date createdTime, Date updatedTime, Date deletedTime) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.isDeleted = isDeleted;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.deletedTime = deletedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(Date deletedTime) {
        this.deletedTime = deletedTime;
    }

    public static Currency valueOf(CurrencyReq req) {
        Currency c = new Currency();
        c.setCode(req.getCode());
        c.setName(req.getName());
        c.setDeleted(false);
        c.setCreatedTime(new Date());
        return c;
    }

}