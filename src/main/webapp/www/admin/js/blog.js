var bloghost="http://"+window.location.host; 
var blog={
		/**
		 * 提示 warning danger success
		 * @param message
		 * @param modeltype
		 */
		nofify:	function(message,modeltype){
			 $.notify({
					title: '<strong>提示</strong>',
					message: message
					},{
						type: modeltype,
						placement:{from:'top',align:'center'},
						animate: {
								enter: 'animated bounceInDown',
								exit: 'animated bounceOutUp'
							}
					}
				);
		},
		/**
		 * 文章喜欢
		 * @param articleid 评论id
		 * @param obj 点赞dom 对象
		 */
		articleSupport:function(articleid,obj){
			if(!blog.islogin()){
				blog.nofify("您还未登录,请先登录!","danger");
				return;
			}
			blogAlert.showLoading("Loading...");
			$.ajax({
				   type: "post",
				   url: bloghost+"/font/articleSupport.do",
				   data: {id:articleid},
				   success: function(msg){
					   blogAlert.closeLoading();
					   var str=$(obj).text().replace(/\D|\W/,"");
					   var total=parseInt(str);
					   if(msg=="+"){
						   blog.nofify("点赞成功!","success");
						   $(obj).html('<span class="glyphicon glyphicon-heart"></span>&nbsp;'+(total+1));
						   $(obj).addClass("active");
						   $(obj).find("span").css({"color":"#e78170"});
						   $(obj).attr("data-original-title","取消喜欢");
					   }else if(msg=="-"){
						   blog.nofify("已取消点赞!","success");
						   $(obj).html('<span class="glyphicon glyphicon-heart"></span>&nbsp;'+(total-1)); 
						   $(obj).removeClass("active");
						   $(obj).find("span").css({"color":"black"});
						   $(obj).attr("data-original-title","喜欢");
					   }
					   else{
						   blog.nofify(msg,"danger");
					   }
				     
				   }
				});
		},
		/**
		 * 评论点赞
		 * @param cid 评论id
		 * @param obj 点赞dom 对象
		 */
		articleCommentSupport:function(cid,obj){
			if(!blog.islogin()){
				blog.nofify("您还未登录,请先登录!","danger");
				return;
			}
			$.ajax({
				   type: "post",
				   url: bloghost+"/font/commentSupport.do",
				   data: {commentId:cid},
				   success: function(msg){
					   blogAlert.closeLoading();
					   var node=$(obj).children("span");
					   var str=$(node).text().replace(/\D|\W/,"");
					   var total=parseInt(str);
					   if(msg=="+"){
						   blog.nofify("点赞成功!","success");
						   $(node).text("("+(total+1)+")");
						   $(node).css("color","red");
					   }else if(msg=="-"){
						   blog.nofify("已取消点赞!","success");
						   $(node).text("("+(total-1)+")")
						   $(node).css("color","black");
					   }
					   else{
						   blog.nofify(msg,"danger");
					   }
				     
				   }
				});
		},
		userLogin:function(){
			window.location='https://51so.info/pub/login.do?url='+ window.location;
		},
		userLogout:function(){
			blogAlert.confirm("是否退出系统?",function(){
				window.location='http://51so.info/font/logout.do';
			});
		},
	/**
	 * 删除自己的评论
	 * @param cid
	 */
	delArticleComment:function(cid){
		if(!blog.islogin()){
			blog.nofify("您还未登录,请先登录!","danger");
			return;
		}
			blogAlert.confirm('你确定要删除选中记录吗？', function(result){
				if(result){
					blogAlert.showLoading("正在处理...");
					$.ajax({
						   type: "post",
						   url: bloghost+"/font/delComments.do",
						   data: {id:cid},
						   success: function(msg){
							   blogAlert.closeLoading();
							   if(msg=="success"){
								   blog.nofify("操作成功!","success");
								   $("#"+cid).remove();
							   }else{
								   blog.nofify(msg,"danger");
							   }
						     
						   }
						});
				}
			});	
		},
		/**
		 * 是否已经登录
		 * @returns {Boolean}
		 */
		islogin:function(){
			var regx=/^((https|http)?:\/\/)51so\.info\/admin.*/;
			if(regx.test(window.location+"")){return true;}
			var result=$("#menu_user_info");
			return result.length>0;
		},
		faceBox:function(callback){
			var url=bloghost+'/pub/emojify_dialog.do?callback='+callback;
      	    $.fancybox.open({
      	        href: url,
      	        type: 'iframe',
      	        padding: 10,
      	        scrolling: 'no',
      	        fitToView: true,
      	        width: 450,
      	        height: 320,
      	        autoSize: false,
      	        closeClick: false,
      	        iframe: {
      	       		 scrolling :'no',
      	       		 preload : true
      	       		 }
      	    });
		}
		
};
