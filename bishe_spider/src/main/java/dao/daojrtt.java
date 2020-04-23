package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import pojo.news;

import java.beans.PropertyVetoException;

/**
 * @program: bishe-parent
 * @description: 今日头条dao
 * @author: xiaobai
 * @create: 2020-04-22 14:41
 **/
public class daojrtt extends JdbcTemplate {
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

    public daojrtt() {
        super(comboPooledDataSource);
    }
    public void addkeji(news news){
        String sql="INSERT INTO wynewskeji VALUES(?,?,?,?,?,?,?)";
        update(sql,news.getId(), news.getTitle(), news.getTime(), news.getSource(), news.getContent(), news.getEditor(), news.getDocurl());
    }
    public void addcaijing(news news){
        String sql="INSERT INTO wynewscaijing VALUES(?,?,?,?,?,?,?)";
        update(sql,news.getId(), news.getTitle(), news.getTime(), news.getSource(), news.getContent(), news.getEditor(), news.getDocurl());
    }
    public void addshishang(news news){
        String sql="INSERT INTO wynewsshishang VALUES(?,?,?,?,?,?,?)";
        update(sql,news.getId(), news.getTitle(), news.getTime(), news.getSource(), news.getContent(), news.getEditor(), news.getDocurl());
    }
}
