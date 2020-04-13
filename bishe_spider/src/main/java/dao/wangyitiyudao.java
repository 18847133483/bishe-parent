package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import pojo.news;

import java.beans.PropertyVetoException;

public class wangyitiyudao extends JdbcTemplate{
    private static ComboPooledDataSource comboPooledDataSource;
    static {
        try {
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
            comboPooledDataSource.setUser("root");
            comboPooledDataSource.setPassword("xiaobai");
            comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/news?charactorEncoding=utf-8");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    public wangyitiyudao(){
        super(comboPooledDataSource);
    }
    public void savenew(news news){
        String sql = "insert into wynewstiyu values(?,?,?,?,?,?,?)";
        int update = update(sql, news.getId(), news.getTitle(), news.getTime(), news.getSource(), news.getContent(), news.getEditor(), news.getDocurl());

    }
}
