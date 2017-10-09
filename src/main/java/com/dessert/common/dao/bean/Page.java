package com.dessert.common.dao.bean;

import java.util.List;
import java.util.Map;

public class Page<T> {

    //当前页
    private int currentPage = 1;

    //每页数据容量
    private int pageSize = 10;

    //页数
    private int pageCount = 1;

    //总记录
    private int recordCount;

    private Map<String, Object> statisMap;

    //每页的数据以List形式放入
    private List<T> pageList;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(pageCount<=-1){
            return ;
        }
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize<=0){
            return ;
        }
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        if(recordCount<=-1){
            return ;
        }
        this.recordCount = recordCount;
    }

    public Map<String, Object> getStatisMap() {
        return statisMap;
    }

    public void setStatisMap(Map<String, Object> statisMap) {
        this.statisMap = statisMap;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }
}
