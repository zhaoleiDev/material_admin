package com.zhaolei.material.admin.domain.base;

import java.io.Serializable;

/**
 * @author ZHAOLEI
 */
public class Page implements Serializable {
    private static final long serialVersionUID = -9084355320648271227L;
    private int pageNum;
    private int pageSize;
    private int total;


    public Page(int pageNum, int pageSize, int total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

    public Page() {
    }

    public Page(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }


    public int getPageSize() {
        return pageSize;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
