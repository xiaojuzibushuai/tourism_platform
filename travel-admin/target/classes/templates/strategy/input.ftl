<html lang="en">
<head>
    <title>攻略编辑</title>
    <#include "../common/header.ftl"/>

    <link type="text/css" rel="stylesheet" href="/js/plugins/uploadifive/uploadifive.css" />
    <script type="text/javascript" src="/js/plugins/uploadifive/jquery.uploadifive.min.js"></script>

    <script src="/js/ckeditor/ckeditor.js"></script>
    <script>

        //表单提交验证
        $(function () {

            //富文本框图片配置
            var ck = CKEDITOR.replace( 'content',{
                filebrowserBrowseUrl: '/strategy/info',
                filebrowserUploadUrl: '/uploadImg_ck'
            });

            //图片上传
            $('.imgBtn').uploadifive({
                'auto' : true,  //自动发起图片上传请求
                'uploadScript' : '/uploadImg',   //处理上传文件的请求路径
                buttonClass:"btn-link",
                'fileObjName' : 'pic',   //上传文件参数名
                'buttonText' : '浏览...',
                'fileType' : 'image',
                'multi' : false,
                'fileSizeLimit'   : 5242880,
                'removeCompleted' : true, //取消上传完成提示
                'uploadLimit' : 1,
                //'queueSizeLimit'  : 10,
                'overrideEvents': ['onDialogClose', 'onError'],    //onDialogClose 取消自带的错误提示
                'onUploadComplete' : function(file, data) {
                    $("#imgUrl").attr("src" ,data);  //data约定是json格式 图片地址
                    $("#coverUrl").val(data);

                },
                onFallback : function() {
                    $.messager.alert("温馨提示","该浏览器无法使用!");
                }
            });

            //保存
            $("#btn_submit").click(function () {

                //异步提交时， 富文本框可能出问题
                $("#content").val(ck.getData())

                $("#editForm").ajaxSubmit(function (data) {
                    console.log(data);
                    if(data.code == 200){
                        window.location.href = "/strategy/list";
                    }else{
                        $.messager.alert("温馨提示", data.msg);
                    }
                })
            })

        })


    </script>
</head>
<body>
<!--设置菜单回显-->
<#assign currentMenu = 'strategy'>
<div class="container-fluid " style="margin-top: 20px">
<#include "../common/top.ftl"/>
    <div class="row">
        <div class="col-sm-2">
        <#include "../common/menu.ftl"/>
        </div>
        <div class="col-sm-10">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">攻略编辑</h1>
                </div>
            </div>
            <div class="row col-sm-10">
                <form class="form-horizontal" action="/strategy/saveOrUpdate" method="post" id="editForm">
                    <input type="hidden" value="${(strategy.id)!}" name="id">
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">标题：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="title" name="title" value="${(strategy.title)!}" placeholder="请输入攻略标题">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">副标题：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="subTitle" name="subTitle"  value="${(strategy.subTitle)!}" placeholder="请输入攻略副标题">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">封面：</label>
                        <div class="col-sm-8">
                            <input type="hidden"  class="form-control" id="coverUrl"  name="coverUrl" value="${(strategy.coverUrl)!}" >
                            <img src="${(strategy.coverUrl)!'/images/default.jpg'}" width="100px" id="imgUrl">
                            <button type="button" class="imgBtn">浏览</button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">分类：</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="catalog" name="catalogId">
                                <option value="-1">--请选择--</option>

                                <#list catalogs as c >
                                    <optgroup label="${c.destName!}">
                                        <#list c.catalogList as ml>
                                            <option value="${ml.id}">${ml.name}</option>
                                        </#list>
                                    </optgroup>
                                </#list>

                            </select>
                        </div>
                        <script>
                            $("#catalog").val('${(strategy.catalogId)!}');
                        </script>
                    </div>


                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">主题：</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="theme" name="themeId">
                                <option value="-1">--请选择--</option>
                                <#list themes as t>
                                    <option value="${t.id}">${t.name}</option>
                                </#list>
                            </select>

                            <script>
                                $("#theme").val('${(strategy.themeId)!}');
                            </script>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dept" class="col-sm-2 control-label">状态：</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="state" name="state">
                                <option value="0">待发布</option>
                                <option value="1">发布</option>
                            </select>
                        </div>

                        <script>
                            $("#state").val(${(strategy.state)!});
                        </script>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">摘要：</label>
                        <div class="col-sm-8">
                            <textarea  class="form-control" name="summary" >${(strategy.summary)!}</textarea>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <textarea id="content" name="content.content" class="ckeditor">${(strategy.content.content)!}</textarea>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-8">
                            <button id="btn_submit" class="btn btn-primary" type="button">保存</button>
                            <button type="reset" class="btn btn-danger">重置</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
