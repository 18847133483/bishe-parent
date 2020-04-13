package wangyi;

import com.google.gson.Gson;
import dao.wangyidao;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pojo.news;
import redis.clients.jedis.Jedis;
import utils.HttpClientUtils;
import utils.JedisUtils;
import utils.TimeUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static changliang.redischangliang.WANGYI_XINWEN;

public class wyxinwen {
    private static int count = 0;

    public static void xiaomain(wangyidao wangyidao) throws IOException {
        //确定url
        String url = "https://temp.163.com/special/00804KVA/cm_yaowen20200213.js";
        pagewangyi(url, wangyidao);
        System.out.println("新闻" + count);
    }

    public static void pagewangyi(String indexurl, wangyidao wangyidao) throws IOException {
        String url = indexurl;
        int page = 2;
        while (true) {
            String doGet = HttpClientUtils.doGet(url);
            if (StringUtils.isEmpty(doGet)) {
                break;
            }
            jiexijosnnews(doGet, wangyidao);
            String pagestring = "";
            if (page < 10) {
                pagestring = "0" + page;
            } else {
                pagestring = page + "";
            }
            page++;
            url = "https://temp.163.com/special/00804KVA/cm_yaowen20200213_" + pagestring + ".js";
        }
    }

    private static void jiexijosnnews(String doGet, wangyidao wangyidao) throws IOException {
        //处理josn字符串,转换成格式良好的josn数组
        int indexOf = doGet.indexOf("(");
        int lastIndexOf = doGet.lastIndexOf(")");
        String substring = doGet.substring(indexOf + 1, lastIndexOf);
        //遍历josn数据
        Gson gson = new Gson();
        List<Map<String, Object>> list = gson.fromJson(substring, List.class);
        for (Map<String, Object> news : list) {
            //获取每条新闻的url
            String url = (String) news.get("docurl");
            if (url.contains("photoview") ||
                    url.contains("article/detail") ||
                    url.contains("c.m.163.com/") ||
                    url.contains("live.163.com") ||
                    url.contains("v.163.com")) {
                continue;
            }
            //过滤已经怕去过的url
            boolean yijingpaqu = yijingpaqu(url);
            if (yijingpaqu) {
                continue;
            }
            count++;
            //获取每条新闻的html页面
            jiexinews(url, wangyidao);
        }
    }

    private static boolean yijingpaqu(String url) {
        Jedis jedis = JedisUtils.getJedis();
        Boolean sismember = jedis.sismember(WANGYI_XINWEN, url);
        jedis.close();
        return sismember;
    }

    private static void jiexinews(String docurl, wangyidao wangyidao) throws IOException {
        news news = new news();
        String doGet = HttpClientUtils.doGet(docurl);
        Document document = Jsoup.parse(doGet);
        //标题
        String title = document.select("#epContentLeft h1").text();
        //事时间
        String timeandsource = document.select(".post_time_source").text();
        String[] split = timeandsource.split("　来源: ");
        String time = split[0];
        //来源
        String source01 = document.select(".left").text();
        String source = source01.replace("本文来源：", "");
        //内容
        String content = document.select("#endText p").text();
        //编辑
        String editor = document.select(".ep-editor").text().replace("责任编辑：", "");
        TimeUtil timeUtil = new TimeUtil();
        String id = timeUtil.suijishu();
        news.setId(id);
        news.setTitle(title);
        news.setTime(time);
        news.setSource(source);
        news.setContent(content);
        news.setEditor(editor);
        news.setDocurl(docurl);
        wangyidao.savexinwen(news);
        savetoredis(docurl);
    }

    private static void savetoredis(String docurl) {
        Jedis jedis = JedisUtils.getJedis();
        jedis.sadd(WANGYI_XINWEN, docurl);
        jedis.close();
    }
}
