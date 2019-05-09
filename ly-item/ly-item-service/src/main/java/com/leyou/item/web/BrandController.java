package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:    品牌 controller
 */

@RestController
@RequestMapping("brand")
class BrandController {

    @Autowired
    private BrandService mBrandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryCategoryListByPid(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ){
//        return ResponseEntity.ok(mBrandService.queryBrandByPageAndSort(page, rows, sortBy, desc, key));
        PageResult<Brand> result = mBrandService.queryBrandByPage(page, rows, sortBy, desc, key);
        if (result == null || result.getItems() == null || result.getItems().size() < 1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(result);
    }

    //添加品牌
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        mBrandService.saveBrand(brand, cids);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //修改品牌
    @PutMapping
    public ResponseEntity<Void> changeBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        mBrandService.changeBrand(brand, cids);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //删除品牌
    @DeleteMapping
    public ResponseEntity<Void> deleteBrand(@RequestBody Map<String, Long> map) {
        //System.out.println(map);
        Long bid = map.get("bid");
        mBrandService.deleteBrand(bid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
