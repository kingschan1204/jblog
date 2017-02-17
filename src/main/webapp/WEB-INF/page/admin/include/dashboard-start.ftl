<!-- 左边导航 -->
		<div class="navigate-panel" >
			<div class="row nav-profile">
				<img src="${BLOG_CURRENT_USER.userProfileImg}<#if AdminWebSite.user.userProfileImg?index_of("res.51so.info")!=-1>_profile.100X100</#if>" align="bottom" width="100" height="100" class="center-block"/>
				<center class="dashboard_title">${AdminWebSite.websiteTitle}</center>
			</div>
			<div class="nav-folder" id="blog_main_li" onclick="window.location='${webroot}/admin/main.do'">
				<a style="color:#aeb2b7;"><span class="glyphicon glyphicon-home"></span>&nbsp;后台首页</a>
			</div>
			<div class="nav-folder" id="write_article_li" onclick="window.location='${webroot}/admin/edit_article.do'">
				<a style="color:#aeb2b7;"><span class="glyphicon glyphicon-pencil"></span>&nbsp;写日志</a>
			</div>
			<!---->
<div class="panel-group" id="accordion">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
               		<span class="glyphicon glyphicon-align-justify"></span>&nbsp;日志管理
                </a>
            </h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse">
            <div class="panel-body">
				<a id="category_li" class="nav-folder-item" href="${webroot}/admin/category_list.do"><span class="glyphicon glyphicon-th-large"></span>&nbsp;分类管理</a>
	            <a id="article_li" class="nav-folder-item"  href="${webroot}/admin/article_list.do"><span class="glyphicon glyphicon-file"></span>&nbsp;文章管理</a>
	            <a id="blog_comment_li" class="nav-folder-item" href="${webroot}/admin/article_comments.do"><span class="glyphicon glyphicon-comment"></span>&nbsp;评论管理</a>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                <span class="glyphicon glyphicon-bookmark"></span>&nbsp;书签管理
            </a>
            </h4>
        </div>
        <div id="collapseTwo" class="panel-collapse collapse">
        <div class="panel-body">
            <a id="bookmark_folder_li" class="nav-folder-item" href="${webroot}/admin/bookmark_folder_list.do"><span class="glyphicon glyphicon-book"></span>&nbsp;书签包管理</a>
            	<a id="bookmark_li" class="nav-folder-item" href="${webroot}/admin/bookmark_list.do"><span class="glyphicon glyphicon-bookmark"></span>&nbsp;书签管理</a>
        </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                <span class="glyphicon glyphicon-cog"></span>&nbsp;设置
                </a>
            </h4>
        </div>
        <div id="collapseThree" class="panel-collapse collapse">
            <div class="panel-body">
               <a  id="bolg_profile_li"  class="nav-folder-item" href="${webroot}/admin/profile.do" ><span class="glyphicon glyphicon-user"></span>&nbsp;个人信息</a>
               <a  class="nav-folder-item" data-toggle="modal" data-target="#myModal" href="#" ><span class="glyphicon glyphicon-lock"></span>&nbsp;修改密码</a>
       			<a id="bolg_info_li" class="nav-folder-item" href="${webroot}/admin/website_info.do"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;博客信息</a>
       			<a id="blog_res_li" class="nav-folder-item" href="${webroot}/admin/blogres_list.do"><span class="glyphicon glyphicon-folder-open"></span>&nbsp;资源管理</a>	            
            	<a id="blog_upload_page" class="nav-folder-item" href="${webroot}/admin/upload_page.do"><span class="glyphicon glyphicon-upload"></span>&nbsp;文件上传</a>
            </div>
        </div>
    </div>
    
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                <span class="glyphicon glyphicon-search"></span>&nbsp;报表
                </a>
            </h4>
        </div>
        <div id="collapseFour" class="panel-collapse collapse">
            <div class="panel-body">
                 <a id="blog_access_log_li" class="nav-folder-item" href="${webroot}/admin/report/accessLog.do"><span class="glyphicon glyphicon-time"></span>&nbsp;访客记录</a>
            	 <a id="blog_report_li" class="nav-folder-item" href="${webroot}/admin/report/page.do"><span class="glyphicon glyphicon-search"></span>&nbsp;网站报表</a>
            </div>
        </div>
    </div>
    
</div>
			<div class="nav-folder" onclick="<#if AdminWebSite??>window.open('http://${AdminWebSite.websiteName}.${host}');<#else>javascript:alert('你还未创建博客!');</#if>">
				<span class="glyphicon glyphicon-globe"></span>&nbsp;我的博客
			</div>
			<div class="nav-folder" onclick="window.open('http://${host}');">
				<span class="glyphicon glyphicon-globe"></span>&nbsp;平台首页
			</div>
			<div class="nav-folder" style="color:red;" onclick="blog.userLogout();">
				<span class="glyphicon glyphicon-remove"></span>&nbsp;退出
			</div>		
				
			<!---->
	
    
    
    
			
		</div>
		<div class="page-content" id="page-content">