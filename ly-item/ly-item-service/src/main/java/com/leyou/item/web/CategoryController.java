package com.leyou.item.web;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:    商品分类 controller
 */

@RestController
@RequestMapping("category")
class CategoryController {

    @Autowired
    private CategoryService mCategoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid")Long pid){
        return ResponseEntity.ok( mCategoryService.queryCategoryListByPid(pid));
    }

}
