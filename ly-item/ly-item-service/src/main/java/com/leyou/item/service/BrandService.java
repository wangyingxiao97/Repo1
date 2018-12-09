package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import tk.mybatis.mapper.entity.Example;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:
 */
@Service
public class BrandService {


    @Autowired
    private BrandMapper mBrandMapper;

    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {

        //开启分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().orLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            String sortByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(sortByClause);
        }
        List<Brand> brandList = mBrandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(brandList)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);

        return new PageResult<>(pageInfo.getTotal(), brandList);
    }
}
