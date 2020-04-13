<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${news.title}</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="js/jquery-1.11.3.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div align="center"><h1>${news.title}</h1></div>
<div>
    <div class="col-md-1"></div>
    <div class="col-md-9"><h4>时间:${news.time}</h4></div>
    <div class="col-md-2"><h4>来源:${news.source}</h4></div>
</div>
<div class="formId">
    <table width="100%">
        <p>${news.content}</p>
    </table>
</div>
<div>
    <div class="col-md-8"></div>
    <div class="col-md-4"><h4><font color="white">作者:${news.editor}</font></h4></div>
</div>
</body>
<style type="text/css">
    p {
        text-indent: 2em;
        word-wrap: break-word;
        word-break: normal;
    }

    body {
        background-image: url(img/adv1.jpeg);
    }

    .formId {
        width: 95%;
        height: 95%;
        border: 1px solid #333;
        margin: 5% auto;
        background-color: #aac5e7;
        padding-bottom: 15px;
        padding-top: 30px;
        padding-left: 60px;
        padding-right: 60px;
    }
</style>
</html>
