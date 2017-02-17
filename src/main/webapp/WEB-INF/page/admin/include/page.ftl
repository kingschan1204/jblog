<#--
<select id="pagesize" onchange="pagesizeChange(this)">
	<#assign pagesize=[5,10,15,20,30,40,50]>
	<#list pagesize as index>
		<option <#if limit??&&limit==index>selected="selected"</#if> >${index}</option>
	</#list>
	
</select>
--> 
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
<#if page??>
<nav title="共${page.total!0}条,${page.maxPage}页 ,当前第${page.pageindex}页">
  <ul class="pagination">
    <li >
      <a href="${url}page=1" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
	<#list page.pageindex-6 .. page.pageindex as t>
		<#if (t>=1&&t<page.pageindex)>
			<li><a href="${url}page=${t}">${t}</a></li>
		</#if>
	</#list>
    <li class="active"><a href="${url}page=${page.pageindex}">${page.pageindex}</a></li>
     <#list page.pageindex+1 ..page.pageindex+6 as t>
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
<script>
function pagesizeChange(obj){
	var url=window.location.toString();
	if(url.indexOf("limit=")!=-1){
		var re=/limit=\d+/
		url=url.replace(re,"limit="+obj.value);
	}else if(url.indexOf("?")==-1){
		url+="?limit="+obj.value;
	}else{
		url+="&limit="+obj.value;
	}
	window.location=url;
}
</script>
</#if>