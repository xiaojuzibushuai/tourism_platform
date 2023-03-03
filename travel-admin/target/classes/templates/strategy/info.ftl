<html lang="en">
<head>
    <title>攻略明细管理</title>
    <#include "../common/header.ftl">
    <script src="/js/ckeditor/ckeditor.js"></script>

    <script type="text/javascript">
        function getUrlParam( paramName ) {
            var reParam = new RegExp( '(?:[\?&]|&)' + paramName + '=([^&]+)', 'i' );
            var match = window.location.search.match( reParam );

            return ( match && match.length > 1 ) ? match[1] : null;
        }
        $(function () {
           $("#aaa").click(function () {
               var funcNum = getUrlParam( 'CKEditorFuncNum' );
                var src = $(this).prop("src");
               window.opener.CKEDITOR.tools.callFunction( funcNum, src );
               window.close();
           })
        })
    </script>
</head>
<body>
<!--左侧菜单回显变量设置-->
<#assign currentMenu="strategy">

<div class="container-fluid " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-2">
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-10">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">攻略明细管理</h1>
                </div>
            </div>
            <#setting number_format="#">
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/strategy/list" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <a href="/strategy/input" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>  添加</a>
            </form>

            <img src="/lyf.jpeg" id="aaa">

        </div>
    </div>
</div>

</body>
</html>
