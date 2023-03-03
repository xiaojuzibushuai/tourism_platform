<ul id="menu" class="list-group">
    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#destinationMgr">
            <span>目的地管理</span>
        </a>

        <ul class="in" id="destinationMgr">
            <li class="region"><a href="/region/list">区域管理</a></li>
            <li class="destination"><a href="/destination/list">目的地管理</a></li>
        </ul>
    </li>

    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#strategyMgr">
            <span>攻略管理</span>
        </a>
        <ul class="in" id="strategyMgr">
            <li class="strategyTheme"><a href="/strategyTheme/list">攻略主题管理</a></li>
            <li class="strategyCatalog"><a href="/strategyCatalog/list">攻略分类管理</a></li>
            <li class="strategy"><a href="/strategy/list">攻略明细管理</a></li>
            <li class="strategyCommend"><a href="/strategy/list">攻略推荐管理</a></li>
        </ul>
    </li>

    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#travelMgr">
            <span>游记管理</span>
        </a>
        <ul class="in" id="travelMgr">
            <li class="travel"><a href="/travel/list">游记管理</a></li>
            <li class="travelCommend"><a href="/travel/list">游记推荐管理</a></li>
        </ul>
    </li>
    <li class="list-group-item">
        <a href="#" data-toggle="collapse" >
            <span>评论管理</span>
        </a>
        <ul class="in" >
            <li class="customerReport"><a href="/region/list">攻略评论</a></li>
            <li class="customerReport"><a href="/region/list">游记评论</a></li>
        </ul>
    </li>
    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#advMgr">
            <span>广告管理</span>
        </a>
        <ul class="in" id="travelMgr">
            <li class="banner"><a href="/banner/list">banner管理</a></li>

        </ul>
    </li>
    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#formalCustomerMgr">
            <span>系统管理</span>
        </a>
        <ul class="in" id="formalCustomerMgr">
            <li class="formalCustomer"><a href="#">个人设置</a></li>
            <li class="travel"><a href="#">配置刷新</a></li>
            <li class="travel"><a href="#">数据落地</a></li>
        </ul>
    </li>
</ul>

<!--设置菜单回显-->
<script>
    $(".in li.${currentMenu}").addClass("active");
</script>
