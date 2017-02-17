<!--共${page.total!0}条|共${page.maxPage}页|当前第${page.pageindex}页-->
<#if category??>
    		<#assign url="${url}/category/${category}?">
    	<#elseif fulltext??>
    		<#assign url="${url}/q?q=${fulltext!}&">
</#if>
<#if !url?exists>
	 <#assign url="${webroot}?"/>
</#if>
<nav>
  <ul class="pager">
    <li <#if (page.pageindex-1<=0)>class="disabled"</#if> ><a <#if (page.pageindex-1>0)>href="${url}page=${page.pageindex-1}"</#if> > 
    	<span class="glyphicon glyphicon-chevron-left"></span>上一页</a></li>
    <li <#if (page.pageindex+1>=page.maxPage)>class="disabled"</#if> ><a <#if (page.pageindex+1<page.maxPage)>href="${url}page=${page.pageindex+1}"</#if>>
    	<span class="glyphicon glyphicon-chevron-right"></span>
    	下一页</a></li>
  </ul>
</nav>