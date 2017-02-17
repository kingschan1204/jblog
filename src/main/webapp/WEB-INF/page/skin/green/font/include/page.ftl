<#if category??>
    		<#assign url="${blogprefix}/category/${category}?">
    	<#elseif tag??>
    		<#assign url="${blogprefix}/tags/${tag}?">
    	<#elseif date??>
    		<#assign url="${blogprefix}/date/${date}?">
    	<#elseif fulltext??>
    		<#assign url="${blogprefix}/query?q=${fulltext!}&">
</#if>
<#if !url?exists>
	<#assign url="${blogprefix}/?">
</#if>

<#if (page.maxPage>1)>
<nav>
  <ul class="pager">
    <li <#if (page.pageindex-1<=0)>class="disabled"</#if> ><a <#if (page.pageindex-1>0)>href="${url}page=${page.pageindex-1}${sufix!""}"</#if> > 
    	<span class="glyphicon glyphicon-chevron-left"></span>上一页</a></li>
    <li <#if (page.pageindex+1>page.maxPage)>class="disabled"</#if> ><a <#if (page.pageindex+1<=page.maxPage)>href="${url}page=${page.pageindex+1}${sufix!""}"</#if>>
    	<span class="glyphicon glyphicon-chevron-right"></span>
    	下一页</a></li>
  </ul>
</nav>
</#if>
