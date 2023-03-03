<div style="text-align: center;">
    <ul id="pagination" class="pagination"></ul>
</div>
<script>
//分页
$(function(){
    <#assign a=page.total % page.size == 0>
    $("#pagination").twbsPagination({
            //大爷的吐槽一下为啥fm中不能使用page.pages
            totalPages: ${(page.total % page.size == 0)?string("${(page.total/page.size)?int}", "${(page.total/page.size+1)?int}")},
            startPage : ${page.current},
            visiblePages:5,
            first:"首页",
            prev:"上页",
            next:"下页",
            last:"尾页",
            initiateStartPageClick:false,
            onPageClick:function(event,page){
            $("#currentPage").val(page);
            $("#searchForm").submit();
        }
    });
})
</script>
