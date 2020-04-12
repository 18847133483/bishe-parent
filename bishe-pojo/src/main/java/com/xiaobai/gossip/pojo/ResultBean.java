package com.xiaobai.gossip.pojo;

import java.io.Serializable;

/**
 * 搜索条件封装
 */
public class ResultBean implements Serializable{

    //关键词
    private String keywords;

    //起始时间
    private String startDate;

    // 结束时间
    private String endDate;

    //来源
    private String source;

    //编辑
    private String editor;

    //分页javabean
    private PageBean pageBean;

    //分类
    private String fenlei;

    public String getFenlei() {
        return fenlei;
    }

    public void setFenlei(String fenlei) {
        this.fenlei = fenlei;
    }

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
