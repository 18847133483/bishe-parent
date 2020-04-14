package com.xiaobai.gossip.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

/**
 * 新闻对象
 */
public class News implements Serializable {
    //id
    @Field
    private String id;
    //标题
    @Field
    private String title;
    //url
    @Field
    private String url;
    //内容
    @Field
    private String content;
    //来源
    @Field
    private String source;
    //编辑
    @Field
    private String editor;

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", source='" + source + '\'' +
                ", editor='" + editor + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    //时间
    @Field
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
