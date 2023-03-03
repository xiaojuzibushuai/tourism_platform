<html lang="en">
<head>
    <title>目的地管理</title>
    <#include "../common/header.ftl">
    <script type="text/javascript">
        $(function () {
            //编辑/添加
            $(".inputBtn").click(function () {
                //弹出模态框
                $("#editModal").modal("show");

                $("#treeview").html("");
                //数据复原
                $("#editForm").clearForm(true);

                //目的地回显数据
                var data = $(this).data("json");
                if(data){
                    $("input[name='id']").val(data.id);

                    $("textarea[name='info']").val(data.info);

                }
            })
            
            $(".submitBtn").click(function () {
                //模态框表单提交
                $("#editForm").ajaxSubmit(function (data) {
                    if(data.success){
                        window.location.reload();
                    }else{
                        $.messager.alert("温馨提示", data.msg)
                    }
                })
            })

            //查询
            $(".clickBtn").click(function () {
                var parentId = $(this).data("parentid");
                $("#parentId").val(parentId);
                $("#currentPage").val(1);
                $("#searchForm").submit();
            })

            //修改热门
            $(".hotBtn").click(function () {
                var hot = $(this).data("hot");
                var id = $(this).data("id");
                $.get("/destination/changeHotValue", {hot:hot, id:id}, function (data) {
                    if(data.success){
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
<#assign currentMenu="destination">

<div class="container-fluid " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-2">
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-10">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">目的地管理</h1>
                </div>
            </div>
            <#setting number_format="#">
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/destination/list" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <input type="hidden" name="parentId" id="parentId" value="${qo.parentId!}">
                <div class="form-group">
                    <label for="keyword">关键字:</label>
                    <input type="text" class="form-control" id="keyword" name="keyword" value="${(qo.keyword)!''}" placeholder="请输入名称">
                </div>

                <button type="button" class="btn btn-primary clickBtn" data-parentid="${qo.parentId!}"><span class="glyphicon glyphicon-search"></span> 查询</button>

                <h4>
                    当前位置: <a href="javascript:;" data-parentid="" class="clickBtn">根</a>
                <#list toasts as t>
                         >> <a href="javascript:;" data-parentid="${t.id}" class="clickBtn">${t.name}</a>
                </#list>
                </h4>

            </form>

            <table class="table table-striped table-hover" >
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>名称</th>
                        <th>英文</th>
                        <th>上级</th>
                        <th>操作</th>
                    </tr>
                </thead>
               <#list page.records as entity>
                   <tr>
                       <td>${entity_index+1}</td>
                       <td><a href="/destination/list?parentId=${entity.id}">${entity.name!}</a></td>
                       <td>${entity.english!}</td>
                       <td>${(entity.parentName)!"顶级"}</td>

                       <td>
                           <a class="btn btn-info btn-xs inputBtn" href="javascript:;" data-json='${entity.jsonString!}'>
                               <span class="glyphicon glyphicon-edit"></span> 编辑
                           </a>

                           <a href="javascript:;" class="btn btn-danger btn-xs deleteBtn"
                              data-url="/destination/delete?id=${entity.id}">
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

<#--目的地编辑模态框-->
<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span> </button>
                <h4 class="modal-title">目的地简介</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/destination/updateInfo" method="post" id="editForm">
                    <input type="hidden" value="" name="id">

                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea rows="10" cols="68" name="info"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary submitBtn" >保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" >取消</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
