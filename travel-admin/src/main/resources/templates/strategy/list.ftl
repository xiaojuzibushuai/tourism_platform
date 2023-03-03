<html lang="en">
<head>
    <title>攻略明细管理</title>
    <#include "../common/header.ftl">
    <script type="text/javascript">
        $(function () {
            //查询
            $(".clickBtn").click(function () {
                var parentId = $(this).data("parentid");
                $("#currentPage").val(1);
                $("#searchForm").submit();
            })


            //发布： 改状态
            $(".changeStateBtn").click(function () {
                var state = $(this).data("state");
                var id = $(this).data("id");

                $.get("/strategy/changeState", {state:state, id:id}, function (data) {
                    if(data.code == 200){
                        window.location.reload();
                    }else{
                        $.messager.alert("温馨提示", data.msg);
                    }
                })

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

            <table class="table table-striped table-hover" >
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>封面</th>
                        <th>名称</th>
                        <th>分类</th>
                        <th>主题</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
               <#list page.records as entity>
                   <tr>
                       <td>${entity_index+1}</td>
                       <td><img src="${entity.coverUrl!}" width="100px"> </td>
                       <td>${entity.subTitle!}</td>
                       <td>${(entity.catalogName)!}</td>
                       <td>${(entity.themeName)!}</td>
                       <td>${entity.stateDisplay!}</td>
                       <td>

                           <#if  entity.state == 1>
                            <a class="btn btn-danger btn-xs changeStateBtn" href="javascript:;" data-state='0' data-id="${entity.id}">
                                <span class="glyphicon glyphicon-minus-sign"></span> 下架
                            </a>

                           <#else>
                            <a class="btn btn-default btn-xs changeStateBtn" href="JavaScript:;" data-state="1" data-id="${entity.id}">
                                <span class="glyphicon glyphicon-tag"></span> 发布
                            </a>
                           </#if>


                           <a class="btn btn-info btn-xs inputBtn" href="/strategy/input?id=${entity.id}">
                               <span class="glyphicon glyphicon-edit"></span> 编辑
                           </a>

                           <a href="javascript:;" class="btn btn-danger btn-xs deleteBtn"
                              data-url="/strategy/delete?id=${entity.id}">
                               <span class="glyphicon glyphicon-trash"></span> 删除
                           </a>
                       </td>
                   </tr>
               </#list>
            </table>
            <#--分页-->
            <#include "../common/page.ftl"/>
        </div>
    </div>
</div>

</body>
</html>
