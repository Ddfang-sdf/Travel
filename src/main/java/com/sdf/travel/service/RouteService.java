package com.sdf.travel.service;

import com.sdf.travel.domain.PageBean;
import com.sdf.travel.domain.Route;

public interface RouteService {

    PageBean<Route> getPageBean(Integer currentPage,Integer pageSize,Integer cid);
}
