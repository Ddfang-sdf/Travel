package com.sdf.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * @JsonIgnoreProperties(value = { "handler" })
 *
 * 当json序列化的时候有时会抛出这个异常：
 * No serializer found for class com.xianzhi.jinba.vo.NewNewsFlash$Explain
 * and no properties discovered to create BeanSerializer
 *
 * 这是可能是由于实体类属性有些问题，什么问题我也不太清楚，大概是有些无关紧要的属性不需要json序列化，然后
 * json在序列化这个实体类的时候，就会报错。
 * 给每一个需要被序列化的实体类都添加这个注解可以避免这一问题：
 * @JsonIgnoreProperties(value = { "handler" })
 * 大概作用 就是避免不需要json序列化的属性干扰json序列化的过程
 */
@JsonIgnoreProperties(value = { "handler" })
public class Route implements Serializable {
    private Integer rid;

    private String rname;

    private Double price;

    private String routeintroduce;

    private String rflag;

    private String rdate;

    private String isthemetour;

    private Integer count;

    private Integer cid;

    private String rimage;

    private Integer sid;

    private String sourceid;

    private Category category;

    private Seller seller;

    private List<RouteImg> routeImgs;

    @Override
    public String toString() {
        return "Route{" +
                "rid=" + rid +
                ", rname='" + rname + '\'' +
                ", price=" + price +
                ", routeintroduce='" + routeintroduce + '\'' +
                ", rflag='" + rflag + '\'' +
                ", rdate='" + rdate + '\'' +
                ", isthemetour='" + isthemetour + '\'' +
                ", count=" + count +
                ", cid=" + cid +
                ", rimage='" + rimage + '\'' +
                ", sid=" + sid +
                ", sourceid='" + sourceid + '\'' +
                ", category=" + category +
                ", seller=" + seller +
                ", routeImgs=" + routeImgs +
                '}';
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<RouteImg> getRouteImgs() {
        return routeImgs;
    }

    public void setRouteImgs(List<RouteImg> routeImgs) {
        this.routeImgs = routeImgs;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRouteintroduce() {
        return routeintroduce;
    }

    public void setRouteintroduce(String routeintroduce) {
        this.routeintroduce = routeintroduce;
    }

    public String getRflag() {
        return rflag;
    }

    public void setRflag(String rflag) {
        this.rflag = rflag;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getIsthemetour() {
        return isthemetour;
    }

    public void setIsthemetour(String isthemetour) {
        this.isthemetour = isthemetour;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getRimage() {
        return rimage;
    }

    public void setRimage(String rimage) {
        this.rimage = rimage;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }
}