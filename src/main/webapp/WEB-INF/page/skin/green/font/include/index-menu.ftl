<script>NProgress.start();NProgress.inc();</script>
<!-- Fixed navbar -->
    <nav class="navbar navbar-fixed-top" id="menu" >
      <div class="container">
          <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                  <span class="sr-only">${website.websiteTitle}</span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
              </button>
              <b class="navbar-brand"  title="${website.websiteTitle}">${website.websiteTitle}</b>
          </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li id="menu_home" ><a href="${blogprefix}"><span class="glyphicon glyphicon-home"></span>&nbsp;主页</a></li>
                <li id="menu_article_timeline"><a href="${blogprefix}/article_timeline"><span class="glyphicon glyphicon-file"></span>&nbsp;归档</a></li>
                <li id="menu_lable_article_lis"><a href="${blogprefix}/lable_lis"><span class="glyphicon glyphicon-tags"></span>&nbsp;标签</a></li>
                <li id="menu_blog_timeline"><a href="${blogprefix}/blog-timeline"><span class="glyphicon glyphicon-time"></span>&nbsp;动态</a></li>
                <li id="menu_blog_msgboard"><a href="${blogprefix}/blog-msgboard"><span class="glyphicon glyphicon-comment"></span>&nbsp;留言板</a></li>
			<#if BLOG_CURRENT_USER??>
                <li class="dropdown" id="menu_user_info">
                    <a href="#" class="dropdown-toggle userdrop" data-toggle="dropdown" role="button" aria-expanded="false" title="${BLOG_CURRENT_USER.userScreenName}">
                        <span class="glyphicon glyphicon-user"></span>&nbsp;${BLOG_CURRENT_USER.userScreenName}<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <#if BLOG_CURRENT_USER.userLevel=="admin">
                            <li><a href="${webroot}/admin/edit_article.do" target="_blank"><span class="glyphicon glyphicon-plus"></span>&nbsp;写文章</a></li>
                            <li><a href="http://${host}/admin/article_list.do" target="_blank"><span class="glyphicon glyphicon-th-large"></span>&nbsp;文章管理</a></li>
                            <li><a href="${webroot}/admin/website_info.do" target="_blank"><span class="glyphicon glyphicon-cog"></span>&nbsp;博客设置</a></li>
                        </#if>
                        <li><a href="http://${host}/font/logout.do" style="color:red;"><span class="glyphicon glyphicon-remove"></span>&nbsp;退出</a></li>
                    </ul>
                </li>
			<#else>
                <li><a href="javascript:window.location='http://51so.info/pub/login.do?url='+ window.location"><span class="glyphicon glyphicon-user"></span>&nbsp;<b>登录</b></a></li>
			</#if>
            </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<div class="jumbotron animated pulse" cover="${website.websiteCover!}">
      <div class="container">
      		<div class="row" style="padding-top:30px;">
      			<img src="${website.user.userProfileImg}<#if website.user.userProfileImg?index_of("res.51so.info")!=-1>_profile.150X150</#if>" width="150" height="150" alt="website icon" class="img-circle center-block">
      		</div>
      		<div class="row" >
      			<div class="col-md-12">
      				<div class="row">
      					<h3 class="text-center"><strong>${website.websiteTagline}</strong></h3>
      				</div>
      				<div class="row banner-category" >
		      		<@CountCategory website_id="${website.id}">
				    	  <#list category_lis as category>
			    			<a href="${blogprefix}/category/${category.categoryName}" class="category_list" data-toggle="tooltip" title="${category.categoryRemark}">${category.categoryName}<span>(${category.total})</span></a>
				    	</#list>
				    </@CountCategory> 
		      		</div>
      				<div class="row">
      				<h4>${website.websiteAbout}</h4>
      				</div>
      			</div>
      		</div>
      		<div class="row">
      			${website.websiteNotice!}
      		</div>
      </div>
    </div>
    <script  src="${webroot}/www/skin/green/js/banner.js"></script>