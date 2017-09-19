package com.baipengx.coin.beans;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
public class PageResult<T> {
	@Getter
	@Setter
    private List<T> data = Lists.newArrayList();
	@Getter
	@Setter
    private int total = 0;
	
    private int pageNum;	//一共多少页
    @Getter
    @Setter
    private int pageNo;		//当前是第几页
    @Getter
    @Setter
    private int pageSize;	//每页多少
    
    public int getPageNum(){
		return this.pageNum;
    }
    
	public PageResult(List<T> data, int total, int pageNo, int pageSize) {
		super();
		this.data = data;
		this.total = total;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.pageNum = (total - 1)/pageSize + 1;
	}
}