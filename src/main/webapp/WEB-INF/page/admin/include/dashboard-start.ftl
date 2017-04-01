<!-- 左边导航 -->
		<div class="navigate-panel" >
			<div class="row nav-profile">
                <a href="${webroot}/admin/profile.do">
                    <img src="${BLOG_CURRENT_USER.userProfileImg}<#if AdminWebSite.user.userProfileImg?index_of("res.51so.info")!=-1>_profile.100X100</#if>" align="bottom" width="100" height="100" class="center-block"/>
                    <center class="dashboard_title">${AdminWebSite.websiteTitle}</center>
                </a>
			</div>
            <a href="${webroot}/admin/main.do" >
                <div class="nav-folder" style="margin-top: 40px;" >
                    <span class="glyphicon glyphicon-dashboard"></span>仪表盘
                </div>
            </a>
            <a href="${webroot}/admin/edit_article.do" >
                <div class="nav-folder">
                    <span class="glyphicon glyphicon-pencil"></span>发布文章
                </div>
            </a>
            <a href="${webroot}/admin/category_list.do">
                <div class="nav-folder" >
                    <span class="glyphicon glyphicon-th-large"></span>文章分类
                </div>
            </a>
            <a href="${webroot}/admin/article_list.do">
                <div class="nav-folder" >
                   <span class="glyphicon glyphicon-th-list"></span>文章管理
                </div>
            </a>
            <a href="${webroot}/admin/article_comments.do">
                <div class="nav-folder" >
                    <span class="glyphicon glyphicon-comment"></span>讨论管理
                </div>
            </a>
            <a href="${webroot}/admin/blogres_list.do">
                <div class="nav-folder" >
                    <span class="glyphicon glyphicon-cloud"></span>文件管理
                </div>
            </a>
            <a href="${webroot}/admin/website_info.do">
                <div class="nav-folder" >
                    <span class="glyphicon glyphicon-cog"></span>博客设置
                </div>
            </a>
			<div class="nav-folder" onclick="<#if AdminWebSite??>window.open('http://${AdminWebSite.websiteName}.${host}');<#else>javascript:alert('你还未创建博客!');</#if>">
				<span class="glyphicon glyphicon-globe"></span>我的博客
			</div>
			<div class="nav-folder" style="color:red;" onclick="blog.userLogout();">
				<span class="glyphicon glyphicon-remove"></span>退出
			</div>		
			<!---->
		</div>
		<div class="page-content" id="page-content">