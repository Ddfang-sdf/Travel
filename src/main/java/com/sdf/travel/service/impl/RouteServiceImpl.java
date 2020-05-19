package com.sdf.travel.service.impl;

import com.sdf.travel.dao.RouteMapper;
import com.sdf.travel.domain.PageBean;
import com.sdf.travel.domain.Route;
import com.sdf.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteMapper mapper;

    @Autowired
    private PageBean<Route> pageBean;

    @Override
    public PageBean<Route> getPageBean(Integer currentPage, Integer pageSize, Integer cid) {

        if (cid == null || pageSize == null || currentPage == null)
            throw new RuntimeException("传入参数不能为空");
        //封装总记录数
        Long count = mapper.findCount(cid);

        if (count == null) return null;

        pageBean.setTotalCount(count);

        //封装数据
        Integer start = (currentPage-1)*pageSize;

        List<Route> allByCid = mapper.findAllByCid(cid, start, pageSize);

        if (allByCid == null || allByCid.size() == 0) return null;

        pageBean.setList(allByCid);

        //封装总页数
        Long totalPage = count % pageSize == 0?count/pageSize :(count/pageSize+1);

        pageBean.setTotalPage(totalPage);

        //当前页和每页显示条数
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);


        return pageBean;
    }
}
