package com.sdf.travel.service.impl;

import com.sdf.travel.dao.FavoriteMapper;
import com.sdf.travel.dao.RouteMapper;
import com.sdf.travel.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    RouteMapper routeMapper;

    @Override
    public Boolean isFavorite(Integer rid, Integer uid) {
        return favoriteMapper.findByRidAndUid(rid, uid) != null ? true:false;
    }

    @Transactional
    @Override
    public Boolean addFavorite(Integer rid, Integer uid) {

        try {
            //添加收藏数据
            if (!favoriteMapper.addFavorite(rid, uid))
                return false;


            //增加收藏数
            if (!routeMapper.updateCountByRid(rid))
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


        return true;
    }
}
