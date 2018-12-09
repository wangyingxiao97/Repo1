package com.leyou.item.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * author:  niceyoo
 * blog:    https://cnblogs.com/niceyoo
 * desc:    品牌
 */

@Data
@Table(name="tb_brand")
public class Brand {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private String image;
    private Character letter;

}
