package wangyi.yule;

import com.google.gson.Gson;
import dao.wangyiyuledao;
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

import static changliang.redischangliang.WANGYI_YULE;

public class yuleliebiao {
    private static int count = 0;
    private static dao.wangyiyuledao wangyiyuledao = new wangyiyuledao();

    public static void xiaomain() throws IOException {
        //确定url
        String url = "https://ent.163.com/special/000380VU/newsdata_index.js";
        pagewangyi(url);
        System.out.println("娱乐"+count);
    }

    public static void pagewangyi(String indexurl) throws IOException {
        String url = indexurl;
        int page = 2;
        while (true) {
            String doGet = HttpClientUtils.doGet(url);
            if (StringUtils.isEmpty(doGet)) {
                break;
            }
            jiexijosnnews(doGet);
            String pagestring = "";
            if (page < 10) {
                pagestring = "0" + page;
            } else {
                pagestring = page + "";
            }
            page++;
            url = "https://ent.163.com/special/000380VU/newsdata_index_" + pagestring + ".js";
        }
    }

    private static void jiexijosnnews(String doGet) throws IOException {
        //处理josn字符串,转换成格式良好的josn数组
        int indexOf = doGet.indexOf("(");
        int lastIndexOf = doGet.lastIndexOf(")");
        String substring = doGet.substring(indexOf + 1, lastIndexOf);
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
            jiexinews(url);
        }

    }

    private static boolean yijingpaqu(String url) {
        Jedis jedis = JedisUtils.getJedis();
        Boolean sismember = jedis.sismember(WANGYI_YULE, url);
        jedis.close();
        return sismember;
    }

    private static void jiexinews(String docurl) throws IOException {
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
        wangyiyuledao.savenew(news);
        savetoredis(docurl);
    }

    private static void savetoredis(String docurl) {
        Jedis jedis = JedisUtils.getJedis();
        jedis.sadd(WANGYI_YULE, docurl);
        jedis.close();
    }
}
