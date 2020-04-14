package com.xiaobai.gossip.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class minnews implements Serializable {
    //标题
    @Field
    private String title;
    //内容
    @Field
    private String content;
    //来源
    @Field
    private String source;
    //编辑
    @Field
    private String editor;
    //时间
    @Field
    private String fenlei;

    public minnews() {
    }

    public minnews(String title, String content, String source, String editor, String fenlei) {

        this.title = title;
        this.content = content;
        this.source = source;
        this.editor = editor;
        this.fenlei = fenlei;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getFenlei() {
        return fenlei;
    }

    public void setFenlei(String fenlei) {
        this.fenlei = fenlei;
    }

    @Override
    public String toString() {
        return "minnews{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", source='" + source + '\'' +
                ", editor='" + editor + '\'' +
                ", fenlei='" + fenlei + '\'' +
                '}';
    }
}
