package rmw;

import dao.daojrtt;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo.news;
import redis.clients.jedis.Jedis;
import utils.JedisUtils;
import utils.TimeUtil;
import utils.httpclientutils01;

import java.text.SimpleDateFormat;
import java.util.Date;

import static changliang.redischangliang.WANGYI_CAIJING;
import static changliang.redischangliang.WANGYI_KEJI;

/**
 * @program: bishe-parent
 * @description: 人民网财经爬取
 * @author: xiaobai
 * @create: 2020-04-23 10:38
 **/
public class rmwcaijing {
    private static int count=0;

    public static void xiaomain(daojrtt daojrtt) throws Exception {
        // 需要爬的网页的文章列表
        String indexurl = "http://finance.people.com.cn/GB/70846/index1.html";
        xunhuanpage(indexurl, daojrtt);
        System.out.println("财经"+count);
    }

    private static void xunhuanpage(String indexurl, daojrtt daojrtt) throws Exception {
        String url = indexurl;
        int page = 2;
        while (true) {
            String s = httpclientutils01.doGet(url);
            Document parse = Jsoup.parse(s);
            Elements elements = parse.select(".ej_left ul li a");
            if (elements.size() == 0) {
                break;
            }
            System.out.println(url);
            for (Element element : elements) {
                String url1 = element.attr("href");
                if (url1.indexOf('/') == 0) {
                    url1 = "http://finance.people.com.cn" + url1;
                }
                boolean yijingpaqu = yijingpaqu(url1);
                if (yijingpaqu) {
                    continue;
                }
                if(url1.contains("n/2014")){
                    continue;
                }
                jiexinews(url1, daojrtt);
                //System.out.println(url1);
            }

            url = "http://finance.people.com.cn/GB/70846/index" + page + ".html";

            page++;
//            Thread.sleep(3000);
        }


    }



    private static boolean yijingpaqu(String url) {
        Jedis jedis = JedisUtils.getJedis();
        Boolean sismember = jedis.sismember(WANGYI_CAIJING, url);
//            System.out.println(sismember);
        jedis.close();
        return sismember;
    }


    private static void jiexinews(String newsurl, daojrtt daojrtt) throws Exception {
        news news = new news();
        String url = newsurl;
        String s = httpclientutils01.doGet(url);
        System.out.println(url);
        String content = StringUtils.substringBetween(s, "<div class=\"box_pic\"></div>", "<div class=\"zdfy clearfix\">");
        if (StringUtils.isNotEmpty(s)) {
            Document document = Jsoup.parse(s);
            if (content == null) {
                content = document.select(".box_con p").text();
            }
            String[] split = document.select(".box01").text().split("来源：");
            String time = null;
            String source = null;
            if (split.length == 2) {
                time = document.select(".box01").text().split("来源：")[0];
                source = document.select(".box01").text().split("来源：")[1];
            } else {
                time = document.select(".box01").text().split("来源：")[0];
                source = document.select(".author").text().replace("来源：", "");
            }
            if(StringUtils.isEmpty(time)){
                time="2020年04月04日11:11  ";
            }
            SimpleDateFormat saveformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat rmwformat = new SimpleDateFormat("yyyy'年'MM'月'dd'日'HH:mm ");
            Date da = rmwformat.parse(time);
            time = saveformat.format(da);
            String title = document.select("h1").text();
            String id = TimeUtil.suijishu();
            String editor=null;
            if(document.select(".box_con div").last()!=null){
                editor = StringUtils.substringBetween(document.select(".box_con div").last().text(),"：",")");
            }
            news.setId(id);
            news.setTime(time);
            news.setTitle(title);
            news.setSource(source);
            news.setContent(content);
            news.setEditor(editor);
            news.setDocurl(url);
            if(StringUtils.isNotEmpty(news.getTitle())&&StringUtils.isNotEmpty(news.getEditor())){
                daojrtt.addkeji(news);
                savetoredis(url);
                count++;
            }

        }


    }

    private static void savetoredis(String url) {
        Jedis jedis = JedisUtils.getJedis();
        jedis.sadd(WANGYI_CAIJING, url);
        jedis.close();
    }
}
