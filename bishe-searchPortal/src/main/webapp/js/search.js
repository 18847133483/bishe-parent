//1. 从当前页面, 获取到用户输入内容
// 如何从url路径上获取请求参数: js
var kv = window.location.search;
// alert(kv);
//如果没有传递任何的参数跳转首页
if (kv == null || kv == "" || kv == "?keywords=" || kv.split("&")[0].split("=")[1] == "") {
    window.location.href = "/index.html";

}
var v = kv.split("&")[0].split("=")[1];
var fenlei = kv.split("&")[1].split("=")[1];

//2. 将数据再次回显给搜索框
//回显之前, 需要将数据, 进行url解码
//如果想做全部替换, 需要使用正则表达式


v = decodeURI(v);
//   /\+/g :  是一个正则对象
v = v.replace(/\+/g, " ");
v = v.replace(/%2B/g, "+");

//回显数据
$("#inputSeach").val(v);
$("#fenlei").val(fenlei)

//3. 发送异步请求
ajaxQuery("1", "10");


function ajaxQuery(page, pageSize) {
    //数据清空的操作
    $(".itemList").html("");
    //3.1 获取请求参数
    //3.1.1 获取查询的关键词
    var keywords = $("#inputSeach").val();
    //3.1.2 获取搜索工具: 起止时间
    var startDate = $("[name=startDate]").val();
    var endDate = $("[name=endDate]").val();
    //3.1.3 获取搜索工具: 编辑
    var editor = $("[name=editor]").val();
    //3.1.3 获取搜索工具: 来源
    var source = $("[name=source]").val();

    //3.3 执行异步请求
    var params = {
        "keywords": keywords,
        "startDate": startDate,
        "endDate": endDate,
        "editor": editor,
        "source": source,
        "pageBean.page": page,
        "pageBean.pageSize": pageSize,
        "fenlei": fenlei

    }
    $.post("/ps.action", params, function (data) { // 返回 pageBean对象

        if (data == null || data == "") {
            window.location.href = "/index.html";
            return;
        }

        //alert(data.pageCount)
        $(".toolBoxcount").html("");
        $(".toolBoxcount").html("总共" + (data.pageCount) + "条结果");
        if (data.pageCount == 0) {
            //alert("没数据");
            var divkong = "<div class=\"title\">没有找到相关内容</div>";
            $(".itemList").append(divkong);
        }

        // var zsdiv="<div class=\"toolBoxcount\">asd<span class=\"tool\">搜索工具</span></div>";
        // $(".showTool").append(zsdiv);
        // data 就是返回的json数据 : PageBean对象json后的数据
        $(data.newsList).each(function () {

            //一个news对象, 就是一个新闻,一个新闻其实就是div标签
            var url = this.url;
            url = url.substring(0, 20) + "...";
            var divStr = "<div class=\"item\">\n" +
                "                    <div class=\"title\"><a href=\"queryid.action?id=" + this.id + "&fenlei=" + fenlei + "\">" + this.title + "</a></div>\n" +
                "                <div class=\"contentInfo_src\">\n" +
                "                    <div class=\"infoBox\">\n" +
                "                    <p class=\"describe\">\n" +
                "                   " + this.content + " " +
                "            </p>\n" +
                "                <p><a class=\"showurl\" href=\"queryid.action?id=" + this.id + "&fenlei=" + fenlei + "\">" + url + "&nbsp;&nbsp;&nbsp;" + this.time + "</a> <span class=\"lab\">" + this.editor + " - " + this.source + "</span></p>\n" +
                "                </div>\n" +
                "                </div>\n" +
                "                </div>"
            //     <div class=\"item\">\n" +
            // "                    <div class=\"title\"><a href=\"queryid.action?id=" + this.id + "&fenlei=" + fenlei + "\">" + this.title + "</a></div>\n" +
            // "                <div class=\"contentInfo_src\">\n" +
            // "                    <a href=\"#\"><img src=\"./img/item.jpeg\" alt=\"\" class=\"imgSrc\" width=\"121px\" height=\"75px\"></a>\n" +
            // "                    <div class=\"infoBox\">\n" +
            // "                    <p class=\"describe\">\n" +
            // "                   " + this.content + " " +
            // "            </p>\n" +
            // "                <p><a class=\"showurl\" href=\"queryid.action?id=" + this.id + "&fenlei=" + fenlei + "\">" + url + "&nbsp;&nbsp;&nbsp;" + this.time + "</a> <span class=\"lab\">" + this.editor + " - " + this.source + "</span></p>\n" +
            // "                </div>\n" +
            // "                </div>\n" +
            // "                </div>

            $(".itemList").append(divStr);

        });

        //分页条
        var pageStr = "<ul>";
        // 1.1 上一页 :
        //<li><a href="#">< 上一页</a></li>
        if (data.page > 1) {
            //如何禁用掉某一个元素的属性呢? js:javascript:void(0)
            pageStr += "<li><a href='javascript:void(0)' onclick='ajaxQuery(" + (data.page - 1) + ",10)'>< 上一页</a></li>";
        }

        //1.2 分页码数: 展示七个条码     前减去三   当前页   后加三             1 2 3 4 5 6 7
        //如果总页数就7个:展示全部页面
        if (data.pageNumber <= 7) {
            //直接展示所有的页数
            for (var i = 1; i <= data.pageNumber; i++) {
                if (data.page == i) {
                    pageStr += "<li class='on'>" + i + "</li>";
                } else {
                    pageStr += "<li><a href='javascript:void(0)' onclick='ajaxQuery(" + i + ",10)' >" + i + "</a></li>"
                }

            }

        } else {//总页数大于7

            // 如果用户: 当前页 1~4  展示1~7，显示前7个页码  1 2 3 4 5 6 7
            if (data.page <= 4) {
                for (var i = 1; i <= 7; i++) {
                    if (data.page == i) {
                        pageStr += "<li class='on'>" + i + "</li>";
                    } else {
                        pageStr += "<li><a href='javascript:void(0)' onclick='ajaxQuery(" + i + ",10)' >" + i + "</a></li>"
                    }
                }
            }

            //如果用户: 当前页 大于4,展示: 当前页-3 ~ 当前页+3
            // 1 2 3 4 5 6 7 8 9 10
            if (data.page > 4 && data.page <= data.pageNumber - 3) {

                for (var i = data.page - 3; i <= data.page + 3; i++) {

                    if (data.page == i) {
                        pageStr += "<li class='on'>" + i + "</li>";
                    } else {
                        pageStr += "<li><a href='javascript:void(0)' onclick='ajaxQuery(" + i + ",10)' >" + i + "</a></li>"
                    }

                }
            }

            //如果当前页+3大于等于总页数: 展示 总页数-6， 显示最后7个页码
            if (data.page + 3 >= data.pageNumber) {
                for (var i = data.pageNumber - 6; i <= data.pageNumber; i++) {
                    if (data.page == i) {
                        pageStr += "<li class='on'>" + i + "</li>";
                    } else {
                        pageStr += "<li><a href='javascript:void(0)' onclick='ajaxQuery(" + i + ",10)' >" + i + "</a></li>"
                    }
                }
            }

        }


        //1.3 下一页:
        if (data.page < data.pageNumber) {
            pageStr += "<li><a href='javascript:void(0)' onclick='ajaxQuery(" + (data.page + 1) + ",10)'>下一页 ></a></li>";
        }

        pageStr += "</ul>";

        $(".pageList").html(pageStr);

    }, "json")

}


function ajaxTopQuery() {
    //发送异步请求, 获取热搜词

    $.get("/top.action", function (data) {

        var topList = $(data);
        if (topList.length == 0) {
            //暂时不做任何的处理
            return;
        }

        var topDivStr = "";
        topList.each(function () {
            //this : map
            topDivStr += "<div class='item' ><span style='width:100%;' onclick='topkey()'>" + this.topKeyWords + "</span><span style='float: right; color: red'>" + this.score + "</span></div>";

        });
        $(".recommend").html(topDivStr);


    }, "json")
}

function topkey() {
    // 获取到了关键词
    var val = $(this).html();

    //如何执行查询 : 重新执行页面
    window.location.href = "/list3.html?keywords=" + val;
}