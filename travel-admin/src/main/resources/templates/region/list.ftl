<html lang="en">
<head>
    <title>区域管理</title>
    <#include "../common/header.ftl">
    <link rel="stylesheet" type="text/css" href="/js/plugins/bootstrap-select/bootstrap-select.css"/>
    <script src="/js/plugins/bootstrap-select/bootstrap-select.js"></script>

    <script type="text/javascript">
        $(function () {

            //编辑/添加
            $(".inputBtn").click(function () {
                //数据复原
                $("#editForm").clearForm(true);
                $('#refIds').selectpicker('val', '');
                //区域回显数据
                var data = $(this).data("json");
                if(data){
                    $(".modal-title").html("区域编辑");
                    $("#editForm input[name='id']").val(data.id);
                    $("#editForm input[name='name']").val(data.name);
                    $("#editForm input[name='sn']").val(data.sn);
                    $("#editForm input[name='seq']").val(data.seq);
                    $("#editForm select[name='ishot']").val(data.ishot);
                    $("#editForm textarea[name='info']").val(data.info);

                    var refIds = data.refIds.split(",");
                    $('#refIds').selectpicker('val', refIds);  //插件回显有数组
                    $('#refIds').selectpicker('refresh');   //刷新
                }
                //弹出模态框
                $("#editModal").modal("show");
            })

            //设置热门
            $(".hotBtn").click(function () {
                var hot = $(this).data("hot");
                var id = $(this).data("id");
                $.get("/region/changeHotValue", {hot:hot, id:id}, function (data) {
                    if(data.code == 200){
                        window.location.reload();
                    }else{
                        $.messager.alert("温馨提示", data.msg);
                    }
                })
            })


            //关联地区
            $(".destShowBtn").click(function () {
                $("#refplace").html('');
                var id = $(this).data("id");
                $.get("/region/getDestByRegionId", {rid:id}, function (data) {
                    var html = ''
                    $.each(data, function (index, value) {
                        html +='<a href="javascript:;" class="btn btn-link">' + value.name + '</a>'
                    })
                    $("#refplace").html(html);
                    $("#destModal").modal("show");
                })
            })

            $(".submitBtn").click(function () {
                //模态框表单提交
                $("#editForm").ajaxSubmit(function (data) {
                    console.log(data);
                    if(data.code == 200){
                        window.location.reload();
                    }else{
                        $.messager.alert("温馨提示", data.msg)
                    }
                })
            })
        })
    </script>
</head>
<body>
<!--左侧菜单回显变量设置-->
<#assign currentMenu="region">

<div class="container-fluid " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-2">
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-10">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">区域管理</h1>
                </div>
            </div>
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/region/list" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <a href="JavaScript:;" class="btn btn-success inputBtn"><span class="glyphicon glyphicon-plus"></span>  添加</a>
            </form>

            <table class="table table-striped table-hover" >
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>名称</th>
                        <th>简介</th>
                        <th>关联地区</th>
                        <th>排序</th>
                        <th>操作</th>
                    </tr>
                </thead>
               <#list page.records as entity>
                   <tr>
                       <td>${entity_index+1}</td>
                       <td>${entity.name!}</td>
                       <td>${entity.info!}</td>
                       <td><a href="JavaScript:;" class="destShowBtn" data-id="${entity.id}">查看</a></td>
                       <td>${entity.seq!}</td>
                       <td>
                           <#if  entity.ishot == 1 >
                            <a class="btn btn-success btn-xs hotBtn" href="javascript:;" data-hot='0' data-id="${entity.id}">
                                <span class="glyphicon glyphicon-minus-sign"></span> 取消热门
                            </a>

                           <#else>
                            <a class="btn btn-default btn-xs hotBtn" href="JavaScript:;" data-hot="1" data-id="${entity.id}">
                                <span class="glyphicon glyphicon-tag"></span>设为热门
                            </a>
                           </#if>

                           <a class="btn btn-info btn-xs inputBtn" href="JavaScript:;" data-json='${entity.jsonString}'>
                               <span class="glyphicon glyphicon-pencil"></span> 编辑
                           </a>
                           <a href="javascript:;" class="btn btn-danger btn-xs deleteBtn"
                              data-url="/region/delete?id=${entity.id}">
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



<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span> </button>
                <h4 class="modal-title">区域添加/编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/region/saveOrUpdate" method="post" id="editForm">
                    <input type="hidden" value="" name="id">
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" name="name"  placeholder="请输入区域名称">
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">编号：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="sn" placeholder="请输入区域编号" >
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">排序：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="seq" name="seq" placeholder="请输入序号">
                        </div>
                    </div>


                    <div class="form-group">
                        <label  class="col-sm-3 control-label">是否热门：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="ishot" name="ishot">
                                <option value="1">热门</option>
                                <option value="0">普通</option>
                            </select>
                        </div>
                    </div>


                    <div class="form-group">
                        <label  class="col-sm-3 control-label">关联目的地：</label>
                        <div class="col-sm-6">
                            <select class="form-control selectpicker "  multiple id="refIds" name="refIds" data-live-search="true" title="请选择关联的目的地">
                                <#list dests as d>
                                <option value="${d.id}">${d.name}</option>
                                </#list>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">简介：</label>
                        <div class="col-sm-6">
                            <textarea  class="form-control" name="info"></textarea>
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

<#--关联地区查看-->
<div class="modal fade" id="destModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span> </button>
                <h4 class="modal-title">关联目的地</h4>
            </div>
            <div class="modal-body">
                    <div class="form-group">
                        <div class="col-sm-12" id="refplace" style="width: 100%">

                        </div>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" >确定</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
