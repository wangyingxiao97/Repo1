package com.leyou.common.vo;


import java.util.List;

import lombok.Data;

/**
 * 带分页的返回实体
 * @author niceyoo
 * @param <T>
 */
@Data
public class PageResult<T> {

    private Long total;
    private Long totalPage;
    private List<T> items;

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

}
