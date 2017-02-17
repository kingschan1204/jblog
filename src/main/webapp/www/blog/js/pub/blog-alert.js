bootbox.setLocale("zh_CN");
var blogAlert={
		dialog :null,
		alert:function(msg,color){
			bootbox.alert({message:"<font style='color:"+color+";'>"+msg+"</font>",size: 'small'});
		},
		confirm:function(msg,color,funcallback){
			bootbox.confirm({
			    message:"<font style='color:"+color+";'>"+msg+"</font>",
			    size: 'small',
			    buttons: {
			        confirm: {
			            label: '确认',
			            className: 'btn-success'
			        },
			        cancel: {
			            label: '取消',
			            className: 'btn-danger'
			        }
			    },
			    callback: funcallback
			});
		},
		showLoading:function(msg){
			blogAlert.dialog=bootbox.dialog({
			    message: '<p class="text-center"><img src="/www/showloading/loading.gif"/>&nbsp;'+msg+'</p>',
			    size: 'small',
			    closeButton: false
			});
		},
		closeLoading:function(){
			if(null==blogAlert.dialog)return;
			blogAlert.dialog.modal('hide');
		},
		/**
		 * 
		 * @param message
		 * @param modeltype warning danger success
		 */
		notify:	function(message,modeltype){
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
		}
};