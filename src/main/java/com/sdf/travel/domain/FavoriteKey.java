package com.sdf.travel.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class FavoriteKey implements Serializable {
    private Integer rid;

    private Integer uid;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}