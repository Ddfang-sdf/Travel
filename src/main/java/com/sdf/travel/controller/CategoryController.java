package com.sdf.travel.controller;

import com.sdf.travel.domain.Category;
import com.sdf.travel.service.CategoryService;
import com.sdf.travel.util.ResultInfo;
import com.sdf.travel.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService service;

    private ResultInfo info;

    private String json;

    @RequestMapping("/findAll")
    public void findAll(HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json;charset=utf-8");

        List<Category> categories = service.findAll();

        if (categories.size() == 0)
            throw new RuntimeException("服务器正在维护");

        json = ServletUtil.getJson(categories);
        resp.getWriter().write(json);
    }

}
