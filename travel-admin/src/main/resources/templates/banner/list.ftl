<html lang="en">
<head>
    <title>Banner管理</title>
    <#include "../common/header.ftl">

    <link type="text/css" rel="stylesheet" href="/js/plugins/uploadifive/uploadifive.css" />
    <script type="text/javascript" src="/js/plugins/uploadifive/jquery.uploadifive.min.js"></script>

    <script type="text/javascript">
        var sts;
        var ts;
        $(function () {
            $.get("/banner/getAllType", function (data) {
                if(data.code == 200){
                    var map = data.data;
                    sts = map.sts;  //攻略
                    ts = map.ts;  //游记

                    console.log(sts);
                    console.log(ts);
                }
            })



            $("#typeId").change(function () {
                $("#refid").html('');
                var html = '<option value="-1">--请选择--</option>';
                if(this.value == 1){
                    //游记
                    $.each(ts, function (index, item) {
                        html += '<option value="'+item.id+'">'+item.title+'</option>'
                    })

                }else if(this.value == 2){
                    //攻略
                    $.each(sts, function (index, item) {
                        html += '<option value="'+item.id+'">'+item.title+'</option>'
                    })

                }
                $("#refid").html(html);
            })




            //图片上传
            $('.imgBtn').uploadifive({
                'auto' : true,
                'uploadScript' : '/uploadImg',
                buttonClass:"btn-link",
                'fileObjName' : 'pic',
                'buttonText' : '浏览...',
                'fileType' : 'image',
                'multi' : false,
                'fileSizeLimit'   : 5242880,
                'removeCompleted' : true, //取消上传完成提示
                'uploadLimit' : 10,
                'queueSizeLimit'  : 10,
                'overrideEvents': ['onDialogClose', 'onError'],    //onDialogClose 取消自带的错误提示
                'onUploadComplete' : function(file, data) {
                    $("#imgUrl").attr("src" ,  data);
                    $("#coverUrl").val(  data);

                },
                onFallback : function() {
                    $.messager.alert("温馨提示","该浏览器无法使用!");
                }
            });


            //编辑/添加
            $(".inputBtn").click(function () {
                $("#imgUrl").attr("src", "/images/default.jpg");
                //数据复原
                $("#editForm").clearForm(true);
                $("#refid").html('');

                //Banner回显数据
                var json = $(this).data("json");
                if(json){
                    $("#editForm input[name='id']").val(json.id);
                    $("#editForm input[name='title']").val(json.title);
                    $("#editForm input[name='subtitle']").val(json.subtitle);
                    $("#editForm input[name='coverUrl']").val(json.coverUrl);
                    $("#editForm select[name='state']").val(json.state);
                    $("#editForm select[name='type']").val(json.type);
                    $("#editForm input[name='seq']").val(json.seq);
                    $("#imgUrl").attr("src", json.coverUrl);


                    var html = '<option value="-1">--请选择--</option>';
                    if(json.type == 1){
                        //游记
                        $.each(ts, function (index, item) {
                            html += '<option value="'+item.id+'">'+item.title+'</option>'
                        })

                    }else if(json.type == 2){
                        //攻略
                        $.each(sts, function (index, item) {
                            html += '<option value="'+item.id+'">'+item.title+'</option>'
                        })

                    }
                    $("#refid").html(html);

                    $("#refid").val(json.refid);

                }
                //弹出模态框
                $("#editModal").modal("show");

            })
            
            $(".submitBtn").click(function () {
                //模态框表单提交
                $("#editForm").ajaxSubmit(function (data) {
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
<#assign currentMenu="banner">

<div class="container-fluid " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-2">
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-10">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">Banner管理</h1>
                </div>
            </div>
            <#setting number_format="#">
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/banner/list" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <a href="JavaScript:;" class="btn btn-success inputBtn"><span class="glyphicon glyphicon-plus"></span>  添加</a>
            </form>

            <table class="table table-striped table-hover" >
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>封面</th>
                        <th>标题</th>
                        <th>类型</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
               <#list page.records as entity>
                   <tr>
                       <td>${entity_index+1}</td>
                       <td><img src="${entity.coverUrl!}" width="100px"></td>
                       <td>${entity.title!}</td>

                       <td>${entity.typeDisplay!}</td>
                       <td>${entity.stateDisplay!}</td>

                       <td>
                           <a class="btn btn-info btn-xs inputBtn" href="javascript:;" data-json='${entity.jsonString}'>
                               <span class="glyphicon glyphicon-edit"></span> 编辑
                           </a>
                           <a href="javascript:;" class="btn btn-danger btn-xs deleteBtn"
                              data-url="/banner/delete?id=${entity.id}">
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
                <h4 class="modal-title">banner添加/编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/banner/saveOrUpdate" method="post" id="editForm">
                    <input type="hidden" value="" name="id">
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">标题：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="title" name="title"  placeholder="请输入Banner标题">
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">副标题：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="subTitle" name="subTitle"  placeholder="请输入Banner副标题">
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">封面：</label>
                        <div class="col-sm-6">
                            <input type="hidden"  class="form-control" id="coverUrl"  name="coverUrl" value="" >
                            <img src="/images/default.jpg" width="100px" id="imgUrl">
                            <button type="button" class="imgBtn">浏览</button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">状态：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="stateId" name="state">
                                <option value="0">正常</option>
                                <option value="1">禁用</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">类型：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="typeId" name="type">
                                <option value="-1">--请选择--</option>
                                <option value="1">游记</option>
                                <option value="2">攻略</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">关联文章：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="refid" name="refid">
                                <option value="-1">--请选择--</option>
                            </select>

                        </div>
                    </div>



                    <div class="form-group">
                        <label  class="col-sm-3 control-label">排序：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sequence" name="seq" placeholder="请输入序号">
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
