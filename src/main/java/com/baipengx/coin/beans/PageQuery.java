package com.baipengx.coin.beans;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PageQuery {
	  /**
     * 当前页码
     */
    @Min(value = 1, message = "当前页码不合法")
    @Getter
    @Setter
    private Integer pageNo = 1;

    /**
     * 每页数目
     */
    @Min(value = 1, message = "每页展示数量不合法")
    @Getter
    @Setter
    private Integer pageSize = 100;

    /**
     * 偏移量
     */
    @Setter
    private int offset;

    /**
     * 是否使用排序,用于扩展
     * 1:使用,0:不使用
     */
    @Getter
    @Setter
    private String orderColumn;
    
    @Getter
    @Setter
    private Boolean isAsc;

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }
}
