</div>
<!--start Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close"  data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">修改密码</h4>
         </div>
         <div class="modal-body">
            	<form class="form-horizontal" method="post" action="admin/modify_psw.do" id="editpswform">
					  <div class="form-group">
					    <label  class="col-sm-2 control-label">旧密码</label>
					    <div class="col-sm-8">
					      <input type="password" class="form-control" name="oldpsw" id="oldpsw" placeholder="请输入旧密码" maxlength="20">
					    </div>
					  </div>
					<div class="form-group">
					    <label  class="col-sm-2 control-label">新密码</label>
					    <div class="col-sm-8">
					      <input type="password" class="form-control" name="newpsw" id="newpsw" placeholder="请输入新密码" maxlength="20">
					    </div>
					</div>
					<div class="form-group">
					    <label class="col-sm-2 control-label">确认密码</label>
					    <div class="col-sm-8">
					      <input type="password" class="form-control" name="cnewpsw"  placeholder="请输入新密码" maxlength="20">
					    </div>
					</div>
				</form>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default"  data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="mofifypsw();"> 提交</button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</div><!-- end Modal -->

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
  $('#editpswform').bootstrapValidator({
       message: 'This value is not valid',
       feedbackIcons: {
           valid: 'glyphicon glyphicon-ok',
           invalid: 'glyphicon glyphicon-remove',
           validating: 'glyphicon glyphicon-refresh'
       },
       fields: {
       	oldpsw: {
               message: '密码不合规则!',
               validators: {
                   notEmpty: {
                       message: '请输入旧密码！'
                   }
               }
           },
           newpsw: {
               validators: {
                   notEmpty: {
                       message: '请输入新密码！'
                   },stringLength: {
                       min: 6,
                       max: 30,
                       message: '密码长度为6至20'
                   }
               }
           },
           cnewpsw: {
               validators: {
                   notEmpty: {
                       message: '请再一次输入新密码'
                   },identical: {
                       field: 'newpsw',
                       message: '两次输入新密码不一致!'
                   }
               }
           }
       }
   });
   
   
   function mofifypsw(){
	   var result=$('#editpswform').data('bootstrapValidator').isValid();
	   if(result){
		   $.ajax({
			   type: "post",
			   url: "${webroot}/admin/modify_psw.do",
			   data: {newpsw:$('#newpsw').val(),oldpsw:$('#oldpsw').val()},
			   success: function(msg){
				   if(msg=="success"){
					   $('#myModal').modal('hide');
					    blog.nofify("密码修改成功!","success"); 
					   $('#editpswform')[0].reset();
					   $('#editpswform').data('bootstrapValidator').resetForm();

				   }else{
				   		var msg="<b style='color:red;'>"+msg+"</b>";				   		
				   		 bootbox.alert({message:msg,size: 'small'});
				   }
			   }
			});
	   }else{
		   bootbox.alert('请按要求填写表单信息!');
	   }
   }
</script>