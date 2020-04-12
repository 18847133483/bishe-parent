package com.xiaobai.gossip.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 */
public class PageBean implements Serializable {
    //当前页
    private Integer page = 1;
    //每页显示的条数
    private Integer pageSize = 10;
    // 总条数
    private Integer pageCount;
    //总页数
    private Integer pageNumber;
    //当前页的数据
    private List<News> newsList;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", pageCount=" + pageCount +
                ", pageNumber=" + pageNumber +
                ", newsList=" + newsList.size() +
                '}';
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
