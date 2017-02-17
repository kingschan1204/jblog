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
  <ul class="pagination">
    <li >
      <a href="${url}page=1" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
	<#list page.pageindex-3 .. page.pageindex as t>
		<#if (t>=1&&t<page.pageindex)>
			<li><a href="${url}page=${t}">${t}</a></li>
		</#if>
	</#list>
    <li class="active"><a href="${url}page=${page.pageindex}">${page.pageindex}</a></li>
     <#list page.pageindex+1 ..page.pageindex+3 as t>
	    	<#if (t<=page.maxPage)>
		    		<li><a href="${url}page=${t}">${t}</a></li>
	    	</#if>
	</#list>
    <li>
      <a href="${url}page=${page.maxPage}" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>