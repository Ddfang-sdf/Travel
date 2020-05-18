package com.sdf.travel.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
public class RouteImg implements Serializable {
    private Integer rgid;

    private Integer rid;

    private String bigpic;

    private String smallpic;

    public Integer getRgid() {
        return rgid;
    }

    public void setRgid(Integer rgid) {
        this.rgid = rgid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getBigpic() {
        return bigpic;
    }

    public void setBigpic(String bigpic) {
        this.bigpic = bigpic;
    }

    public String getSmallpic() {
        return smallpic;
    }

    public void setSmallpic(String smallpic) {
        this.smallpic = smallpic;
    }

    @Override
    public String toString() {
        return "RouteImg{" +
                "rgid=" + rgid +
                ", rid=" + rid +
                ", bigpic='" + bigpic + '\'' +
                ", smallpic='" + smallpic + '\'' +
                '}';
    }
}