<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>小橘子旅游平台后台管理系统</title>
    <#include "../common/header.ftl" >
    <script>
        $(function(){
            //查看文章
           $(".lookBtn").click(function () {
               var id = $(this).data('id');
               //根据id查询游记内容
               $.get('/travel/getContentById',{id:id},function (data) {
                   $("#inputModal .modal-body").html(data.data);
                   $("#inputModal").modal('show');
               })
           })

            //审核
           $(".auditBtn").click(function () {
               var id = $(this).data('id');
               var state = $(this).data('state');
               $.get('/travel/audit',{id:id,state:state},function (data) {
                   if(data.code == 200){
                       window.location.reload();
                   }else{
                       $.messager.alert("温馨提示", "操作失败");
                   }
               })
           })
        })
    </script>

</head>
<body>

<div class="container-fluid" style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-2">
            <#assign currentMenu="travel"/>
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-10">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">游记管理</h1>
                </div>
            </div>

            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/travel/list" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <div class="form-group">
                   <select id="stateSelect" name="state" class="form-control">
                       <option value="1">待审核</option>
                       <option value="-1">已拒绝</option>
                   </select>
                   <script>
                       $("#stateSelect").val(${(qo.state)!})
                   </script>
                </div>
                <button id="btn_query" class="btn btn-default">
                    <span class="glyphicon glyphicon-search"></span> 查询
                </button>
            </form>

            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>标题</th>
                    <th>封面</th>
                    <th>地点</th>
                    <th>作者</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <#list page.records as entity>
                    <tr>
                        <td>${entity_index+1}</td>
						<td>${entity.title!}</td>
						<td><img src="${entity.coverUrl!}" width="50px"/></td>
						<td>${(entity.destName)!}</td>
						<td>${(entity.author.nickname)!}</td>
						<td>${entity.stateDisplay}</td>
                        <td>
                            <a class="btn btn-default btn-xs auditBtn" href="JavaScript:;"
                               data-id="${entity.id}" data-state="2">
                            <span class="glyphicon glyphicon-tag"></span> 审核通过
                        </a>
                            |
                           <a class="btn btn-danger btn-xs auditBtn" href="javascript:;"
                              data-id="${entity.id}" data-state="3" >
                               <span class="glyphicon glyphicon-minus-sign"></span> 审核拒绝
                           </a>
                            |
                            <a class="btn btn-danger btn-xs updateStateBtn" href="javascript:;"
                               data-id="${entity.id}" data-state="1" >
                                <span class="glyphicon glyphicon-minus-sign"></span> 下架
                            </a>



                            <a class="btn btn-info btn-xs lookBtn" href="JavaScript:;" data-id="${entity.id}">
                                <span class="glyphicon glyphicon-th"></span> 查看</a>
                        </td>
                    </tr>
                </#list>
            </table>
            <#include "../common/page.ftl"/>
        </div>
    </div>
</div>


<!-- 模态框必须作为body的子标签 -->
<div class="modal fade" id="inputModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">查看文章内容</h4>
            </div>
            <div class="modal-body" >

            </div>
        </div>
    </div>
</div>


<!-- 大图显示 -->
<div id="ShowImage_Form" class="modal fade" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body" >
            </div>
        </div>
    </div>
</div>


<script>
   $("table img").click(function(){
       var src = $(this).attr("src");
       $("#ShowImage_Form .modal-body").html("<img src='"+src+"' class='carousel-inner img-responsive img-rounded' />");
       $("#ShowImage_Form").modal('show');
   })
</script>


<style>
    #inputModal img{
        width: 570px !important;
    }
</style>

</body>
</html>
