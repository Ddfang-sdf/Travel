package com.sdf.travel.service;

public interface FavoriteService {

    Boolean isFavorite(Integer rid,Integer uid);

    Boolean addFavorite(Integer rid,Integer uid);
}
