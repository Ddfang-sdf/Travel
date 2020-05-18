package com.sdf.travel.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
public class Category implements Serializable {
    private Integer cid;

    private String cname;

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                '}';
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}