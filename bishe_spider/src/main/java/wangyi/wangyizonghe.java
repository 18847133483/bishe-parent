package wangyi;

import java.io.IOException;

public class wangyizonghe {
    public static void main(String[] args) throws IOException {
        /**
         * 有选择的爬取
         */
//        Scanner scanner = new Scanner(System.in);
//        int i;
//        while (true) {
//            System.out.println("-------小白专用---------");
//            System.out.println("1.网易娱乐分类");
//            System.out.println("2.网易新闻分类");
//            System.out.println("3.网易时尚分类");
//            System.out.println("4.网易体育分类");
//            System.out.println("5.网易财经分类");
//            System.out.println("6.网易科技分类");
//            System.out.println("7.退出系统");
//            System.out.print("请输入要爬取的新闻的编号:");
//            i = scanner.nextInt();
//            if(i==1){
//                wangyi.yule.yuleliebiao.xiaomain();
//            }else if(i==2){
//                wangyi.xinwen.xinwenliebiao1.xiaomain();
//            }else if(i==3){
//                wangyi.shishang.shishangliebiao1.xiaomain();
//            }else if(i==4){
//                wangyi.tiyu.tiyuliebiao.xiaomain();
//            }else if(i==5){
//                wangyi.caijing.caijingliebiao.xiaomain();
//            }else if(i==6){
//                wangyi.keji.kejiliebiao.xiaomain();
//            }else if(i==7){
//                break;
//            }else{
//                System.out.println("您的输入有误");
//            }
//            System.out.println("");
//        }

        /**
         * 无脑爬取全部
         */
        System.out.println("无脑爬取全部分类");
        wangyi.yule.yuleliebiao.xiaomain();
        wangyi.xinwen.xinwenliebiao1.xiaomain();
        wangyi.shishang.shishangliebiao1.xiaomain();
        wangyi.tiyu.tiyuliebiao.xiaomain();
        wangyi.caijing.caijingliebiao.xiaomain();
        wangyi.keji.kejiliebiao.xiaomain();
        System.out.println("爬取完毕");

    }
}
