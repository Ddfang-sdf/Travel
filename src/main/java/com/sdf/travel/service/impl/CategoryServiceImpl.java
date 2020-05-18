package com.sdf.travel.service.impl;

import com.sdf.travel.dao.CategoryMapper;
import com.sdf.travel.domain.Category;
import com.sdf.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper mapper;

    @Override
    public List<Category> findAll() {
        return mapper.findAll();
    }
}
