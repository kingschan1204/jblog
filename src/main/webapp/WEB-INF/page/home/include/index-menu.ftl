<!-- Fixed navbar -->
    <nav class="navbar navbar-fixed-top" id="menu" >
	 <div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">51so</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#" title="51so" ><span class="glyphicon glyphicon-cloud"></span>&nbsp;51so</a>
		</div>
        <div id="navbar" class="navbar-collapse collapse ">
          <ul class="nav navbar-nav navbar-right" style="margin-right:20px;">
            <li id=""><a href="/" class="navbar-nav-link active"><span class="glyphicon glyphicon-home"></span>&nbsp;首页</a></li>
             <#if BLOG_CURRENT_USER??>
             		<li><a href="http://${host}/admin/main.do"><span class="glyphicon glyphicon-user"></span>&nbsp;${BLOG_CURRENT_USER.userScreenName}</a></li>
	                <li><a href="http://${host}/font/logout.do" style="color:red;"><span class="glyphicon glyphicon-remove"></span>&nbsp;退出</a></li>
	              <#else>
	               <li><a href="javascript:window.location='http://51so.info/pub/login.do?url='+ window.location"><span class="glyphicon glyphicon-user"></span>&nbsp;<b>登录</b></a> </li>
	           </#if>
            
            <li>
            <form class="navbar-form navbar-right" method="get" action="/q">
	             <div class="input-group">
	             <input type="text" class="form-control" name="q" placeholder="全文检索" value="${fulltext!}" id="txt-kw">
			      <div class="input-group-btn">
			        <button type="submit" class="btn btn-default" ><span class="glyphicon glyphicon-search"></span>Search</button>
			      </div><!-- /btn-group -->
			    </div><!-- /input-group -->
	          </form>
            </li>
          </ul>
          
	      
        
        </div><!--/.nav-collapse -->
    </nav>
<div class="jumbotron index-banner">
	<div class="container">
      		<h1>更适合您使用的博客平台</h1>
      		<p>归档、标签、书签、全文搜索、Markdown</p>
      		<h5><code>平台还在开发中,开发功能部分内测中!</code></h5>
      		<p>
      		<a class="btn btn-success" href="javascript:notify('客户端正在开发中','danger');"  target="_blank">下载客户端</a>
      		<a class="btn btn-info" href="javascript:notify('内测中，未开发公测!','danger');" target="_blank">免费注册</a>
      		</p>
      		<h4 style="color:white;"><b>Ta们在这里~</b></h4>
      		<p>
      		<@WebSite >
				<#list website_lis as website>
						<#if website.user.userProfileImg!="">
						<a href="http://${website.websiteName!}.${host!}" target="_blank" >
							<img alt="" class="img-thumbnail"  src="${website.user.userProfileImg}" height="70" width="70" style="height:70px;width:70px;" title="${website.websiteTitle}《${website.websiteTagline}》" data-toggle="tooltip" data-placement="top">
						</a>
						</#if>
				</#list>
			</@WebSite>
			</p>
      </div>
</div>
