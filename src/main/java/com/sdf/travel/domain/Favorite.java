package com.sdf.travel.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
@Component
public class Favorite extends FavoriteKey implements Serializable {
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "date=" + date +
                '}';
    }
}