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

var bm_userres={
    name: "openres",
    data: [{
      name: "openresbtn",
      toggle: true, 
      title: "我的资源",
      icon: "glyphicon glyphicon-user",
      callback: function(e){
    	  var url="";
    	  if(!blog.islogin()){
  			//blog.nofify("您还未登录,请先登录!","danger");
    		  url='http://51so.info/pub/login.do?url='+ bloghost+'/admin/blogres_dialog.do?callback=callfun&multiSelect=false';
  		}
      	 url=bloghost+'/admin/blogres_dialog.do?callback=callfun&multiSelect=false';
        	  $.fancybox.open({
        	        href: url,
        	        type: 'iframe',
        	        padding: 10,
        	        scrolling: 'no',
        	        fitToView: true,
        	        width: 1000,
        	        height: 640,
        	        autoSize: false,
        	        closeClick: false,
        	        iframe: {
        	       		 scrolling :'no',
        	       		 preload : true
        	       		 }
        	    });
      }
    }]
};
var bm_facebox={
	    name: "facebox",
	    data: [{
	      name: "faceboxbtn",
	      toggle: true, 
	      title: "表情",
	      icon: "glyphicon glyphicon-th-large",
	      callback: function(e){
	    	 blog.faceBox('facecallback');
	      }
	    }]
	};

var bm_codeblack={
	    name: "codeblack",
	    data: [{
	      name: "codeblackbtn",
	      toggle: true, 
	      title: "代码块",
	      icon: "glyphicon glyphicon-stop",
	      callback: function(e){
	    	 var txt=e.getContent();
	    	 txt+="\r```\r system.out.print(\"这里添加代码\"); \r```\r";
	    	 e.setContent(txt);
	    	 //funpreview(e);
	      }
	    }]
	};

$.fn.markdown.messages['cn'] = {
	    'Bold': "加粗",
	    'Italic': "斜体",
	    'Heading': "标题",
	    'URL/Link': "超链接",
	    'Image': "图片",
	    'List': "序列",
	    'Preview': "预览",
	    'strong text': "加强",
	    'emphasized text': "强调的文本",
	    'heading text': "标题文本",
	    'enter link description here': "在这里输入链接描述",
	    'Insert Hyperlink': "插入超链接",
	    'enter image description here': "在这里输入图像描述",
	    'Insert Image Hyperlink': "提供Hyperlink形象",
	    'enter image title here': "在这里输入标题图片",
	    'list text here': "列表的文本在这里",
	    'Unordered List':"取消序列",
	    'Ordered List':"序列",
	    'Code':"代码",
	    'Quote':"引用"
	 };

$('#target-editor-twitter').markdown({ 
	language:'cn',
	additionalButtons: [[bm_userres,bm_facebox,bm_codeblack]],
	hiddenButtons:'cmdPreview',
	footer:'<div id="twitter-footer" class="well" style="display:none;"></div><small id="twitter-counter" class="text-success">200 个字符</small>',
	onChange:function(e){
		funpreview(e);
	},
	onFocus: function(e) {
		funpreview(e);
	  },
   onBlur: function(e) {
	   funpreview(e);
   }
});

function funpreview(e){
	var content =marked(e.getContent()),
	content_length = (content.match(/\n/g)||[]).length + content.length
	if (content == '') {
		$('#twitter-footer').hide()
	} else {
		$('#twitter-footer').show().html(content)
	}
	
	if (content_length > 200) {
		$('#twitter-counter').removeClass('text-success').addClass('text-danger').html(content_length-200+' 个字符.')
	} else {
		$('#twitter-counter').removeClass('text-danger').addClass('text-success').html(200-content_length+' 个字符.')
	}
	$('pre code').each(function(i, block) {
	    hljs.highlightBlock(block);
	  });
	emojify.run();
}
function facecallback(key){
	var content=$("#target-editor-twitter").val();
	 content+=" "+key;
	$("#target-editor-twitter").val(content);
	$("#target-editor-twitter").focus();
}
function callfun(data){
	var content=$("#target-editor-twitter").val();
	 content+="![](http://res.51so.info/"+data+" \"\")";
	$("#target-editor-twitter").val(content);
	$("#target-editor-twitter").focus();
}
function replay(username){
	$('body,html').animate({scrollTop:$('#target-editor-twitter').offset().top + 'px'},500);
	$("#target-editor-twitter").val($("#target-editor-twitter").val()+"@"+username+":");
	$("#target-editor-twitter").focus();
}
