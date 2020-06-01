package com.sdf.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(value = { "handler" })
public class Seller implements Serializable {
    private Integer sid;

    private String sname;

    private String consphone;

    private String address;

    @Override
    public String toString() {
        return "Seller{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", consphone='" + consphone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getConsphone() {
        return consphone;
    }

    public void setConsphone(String consphone) {
        this.consphone = consphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}