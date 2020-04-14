package wangyi;

import dao.wangyidao;

import java.io.IOException;

public class zonghui {
    private static wangyidao wangyidao = new wangyidao();

    public static void main(String[] args) throws IOException {
        System.out.println("无脑爬取全部分类");
        wyyule.xiaomain(wangyidao);
        wyxinwen.xiaomain(wangyidao);
        wyshishang.xiaomain(wangyidao);
        wytiyu.xiaomain(wangyidao);
        wycaijing.xiaomain(wangyidao);
        wykeji.xiaomain(wangyidao);
        System.out.println("爬取完毕");

    }
}
