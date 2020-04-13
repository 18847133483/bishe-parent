package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import pojo.news;

import java.beans.PropertyVetoException;

public class wangyidao extends JdbcTemplate {
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

    public wangyidao() {
        super(comboPooledDataSource);
    }

    public void savecaijing(news news) {
        String sql = "INSERT INTO wynewscaijing VALUES(?,?,?,?,?,?,?)";
        update(sql, news.getId(), news.getTitle(), news.getTime(), news.getSource(), news.getContent(), news.getEditor(), news.getDocurl());

    }

    public void savekeji(news news) {
        String sql = "INSERT INTO wynewskeji VALUES(?,?,?,?,?,?,?)";
        update(sql, news.getId(), news.getTitle(), news.getTime(), news.getSource(), news.getContent(), news.getEditor(), news.getDocurl());
    }

    public void saveshishang(news news) {
        String sql = "INSERT INTO wynewsshishang VALUES(?,?,?,?,?,?,?)";
        update(sql, news.getId(), news.getTitle(), news.getTime(), news.getSource(), news.getContent(), news.getEditor(), news.getDocurl());

    }

    public void savetiyu(news news) {
        String sql = "INSERT INTO wynewstiyu VALUES(?,?,?,?,?,?,?)";
        update(sql, news.getId(), news.getTitle(), news.getTime(), news.getSource(), news.getContent(), news.getEditor(), news.getDocurl());

    }

    public void savexinwen(news news) {
        String sql = "INSERT INTO wynewsxinwen VALUES(?,?,?,?,?,?,?)";
        update(sql, news.getId(), news.getTitle(), news.getTime(), news.getSource(), news.getContent(), news.getEditor(), news.getDocurl());

    }

    public void saveyule(news news) {
        String sql = "INSERT INTO wynewsyule VALUES(?,?,?,?,?,?,?)";
        update(sql, news.getId(), news.getTitle(), news.getTime(), news.getSource(), news.getContent(), news.getEditor(), news.getDocurl());

    }
}
