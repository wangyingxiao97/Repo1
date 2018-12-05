package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:    商品分类 service
 */

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper mCategoryMapper;


    public List<Category> queryCategoryListByPid(Long pid){

        Category category = new Category();
        category.setParentId(pid);
        List<Category> categoryList = mCategoryMapper.select(category);
        if(CollectionUtils.isEmpty(categoryList)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FUND);
        }
        return categoryList;
    }



}
