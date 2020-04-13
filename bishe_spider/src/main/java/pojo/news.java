package pojo;

public class news {
    private String id;
    private String title;
    private String time;
    private String source;
    private String content;
    private String editor;

    public news() {
    }

    public news(String id, String title, String time, String source, String content, String editor, String docurl) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.source = source;
        this.content = content;
        this.editor = editor;
        this.url = docurl;
    }

    @Override
    public String toString() {
        return "news{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", source='" + source + '\'' +
                ", content='" + content + '\'' +
                ", editor='" + editor + '\'' +
                ", docurl='" + url + '\'' +
                '}';
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getDocurl() {
        return url;
    }

    public void setDocurl(String docurl) {
        this.url = docurl;
    }

    private String url;
}
