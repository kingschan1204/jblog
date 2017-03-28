</div>


<script type="text/javascript">
function builderindex(){
	bootbox.confirm('是否重新生成索引？', function(result){
		if(result){
			$.ajax({
				   type: "post",
				   url: "${blogprefix}/admin/build_index.do",
				   success: function(msg){
					   if(msg=="success"){
						   blog.nofify("操作成功!","success"); 
					   }else{
						   bootbox.alert(msg );
					   }
				   }
				});
		}
		
	});	
	
}
</script>
<script src="http://${host}/www/admin/js/controlPanel.js"></script>