emojify.setConfig({emojify_tag_type:'div', img_dir: bloghost+'/www/emojify/images/emoji'});
var renderer_zai30 = new marked.Renderer();
marked.setOptions({
    renderer: renderer_zai30,
    gfm: true,
    tables: true,
    breaks: true,//回车换成br
    pedantic: false,
    sanitize: true,
    smartLists: true,
    smartypants: false
});
$(function () { 
	emojify.run();
	$(".atlink").each(function(){
		$(this).attr({
			"data-toggle":"popover" ,
			"data-placement":"top",
			"data-html":"true",
			"data-trigger":"click",
			"data-title":$(this).text()
		});
		$(this).mouseover(function(){
			this.click();
		});
		$(this).on('shown.bs.popover', function () {
			if($(this).attr("load")=="true"){return;}
			var u=$(this).text().replace("@","");
			var tip =this;
			$.ajax({
				   type: "get",
				   url: bloghost+"/pub/uinfo_card.do",
				   data: {username:u},
				   dataType:"html",
				   success: function(result){
					 $(tip).attr({
							"data-original-title":u,
							"data-content":result,
							"load":true
						});
					 $(tip).popover('show');
				   }
				});
		});
	});
	//set code light
	$('#article-content-div pre').each(function(i, block) {
	    hljs.highlightBlock(block);
	  });
	//set table css
	$('#article-content-div table').each(function(i) {
	    $(this).addClass("table table-bordered table-hover table-striped table-condensed");
	  });
	//set img
	//$('#article-content-div img').viewer('zoomTo', 1);
	$('.msgboard_reply_list li img').each(function(){
		if(!$(this).hasClass("emoji")){
			$(this).attr({"title":"点我放大图片"});
			$(this).click(function(){$(this).zoomify('zoom');}); 
		}
	});
	$('.info img').each(function(){
		if(!$(this).hasClass("emoji")){
			$(this).addClass("info_img");
			$(this).attr({"title":"点我放大图片"});
			$(this).click(function(){$(this).zoomify('zoom');}); 
		}
	});
	
	
	
	//set like
	var like=$("#btn_like").attr("data-like");
	if(like=="true"){
		$("#btn_like").addClass("active");
		$("#btn_like span").css({"color":"#e78170"});
		$("#btn_like").attr("title","取消喜欢");
	}
	//set reply support
	$("span[exists='true']").each(function(){
		$(this).css({"color":"red"});
	});
	//right navigate
	$(".right-nav").each(function(){
		var h=parseInt($(this).css("height"));
		if(h<600){
			$(this).css("overflow","hidden");
		}
	});
	$(".article-nav").each(function(){
		var href=$(this).attr("href");//;
		if(href){
			href=href.replace("#","");
			$(this).attr("title",href);
			$(this).click(function(){
				$("body,html").animate({scrollTop:$("a[name='"+href+"']").offset().top-60 + "px"},500);
			});
		}
	});
	$("a[h1]").css({"font-weight":"bold","color":"#996699"});
	$("a[h2]").css("color","#ec6149");
	$("a[h3]").css("color","#333333");
	$("[data-toggle='tooltip']").tooltip(); 
	$("[data-toggle='popover']").popover(); 
	$("#right-nav").scrollFix();
	$('#article-content-div img').click(function(){
		// build items array
		var items = new Array();
		$('#article-content-div img').each(function(){
			items.push({
		        src: $(this).attr("src"),
		        title:$(this).attr("alt"),
		        w: this.width,
		        h: this.height
		    });
		});
		for(var i=0;i<items.length;i++){
			if(this.src==items[i].src){
				// define options (if needed)
				var options = {
				    // optionName: 'option value'
				    // for example:
				    index: i // start at first slide
				};
				var pswpElement = document.querySelectorAll('.pswp')[0];
				// Initializes and opens PhotoSwipe
				var gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
				gallery.init();
			}
		}
	});
	//set bootstrap 
	$(document).off('click.bs.dropdown.data-api');
    var $dropdownLi = $('li.dropdown');
    $dropdownLi.mouseover(function() {
        $(this).addClass('open');
    }).mouseout(function() {
        $(this).removeClass('open');
    });
	
    
    $(".popover-content img").click(function() {
    		$('#myPopover1b').popoverX('hide');
    		var content=$("#discussTxt").val();
    		$("#discussTxt").val(content+this.alt+" ");
    		$("#discussTxt").focus();
    	});
    
});
/**
 * 发送
 */
function reply(){
	var content=$("#discussTxt").val().trim();
	if(content.length<3){
		blogAlert.notify("至少输入3个字符","danger");
		return;
	}else if(content.length>200){
		blogAlert.notify("最多不能超200个字符","danger");
		return;
	}
	$("#commentForm1").submit();
}
/**
 * 取消评论
 */
function discussCancel(){
	$("#replyroot").val("");
	$("#discussTxt").val("");
	$("#discussTxt").focus();
}
function discussPreview(obj){
	var content =marked(obj.value);
	$("#discussPreview").html(content);
	$('pre code').each(function(i, block) {
	    hljs.highlightBlock(block);
	  });
	emojify.run();
}
/**
 * 回复某一条
 * @param root 回复哪一条
 * @param user 回复哪个用户
 */
function replyToSomeone(root,user){
	$("#replyroot").val(root);
	var content=$("#discussTxt").val();
	$("#discussTxt").val(content+"@"+user+" ");
	$("#discussTxt").focus();
}
function loadReply(id,max,obj){
	var page=parseInt($(obj).attr("page"));
	if(max<=10||page*10>=max){
		blogAlert.notify("没有更多了!","danger");
		return;
	}
	//    
	var next=page+1;
	blogAlert.showLoading("正在加载请稍后...");
	$.ajax({
		   type: "post",
		   url: bloghost+"/pub/loadArticleDiscussReply.do",
		   data: {page:next,root:id,articleId:$("#articleId").val()},
		   dataType:"html",
		   success: function(result){
			   blogAlert.closeLoading();
			   if(result=="error"){
				   blogAlert.notify("服务端口出错!","danger");
				   return;
			   }
			   $(obj).attr("page",next);
			   $(obj).parent().find(".msgboard_reply_list").append(result);
		   }
		});
}

