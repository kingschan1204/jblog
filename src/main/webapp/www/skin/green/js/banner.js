var device=new UAParser().getResult().device.type;
var iscomputer=(device==undefined||device=="computer")?true:false;
if($(".jumbotron").length>0){
	  var host="http://"+window.location.host; 
	  var cover=$(".jumbotron").attr("cover").trim();
	  if(iscomputer){
		  //电脑访问如没有设置banner，用默认的banner
		  if(cover==""){
			  cover="green-h480.jpg";
		  }
		  $(".jumbotron").css({
			  "background-image":"url("+host+"/www/banner/"+cover+")",
			  "background-repeat":"no-repeat"
		  });
		  var maxheight=cover.match(/-h\d+/);
		  if (maxheight&&maxheight.length>0) {
			  var v=parseInt(maxheight[0].match(/\d+/)[0]);
			  var minv=v-50;
			  $(".jumbotron").css({"max-height":v+"px","min-height":minv+"px", "background-size":"100% "+minv+"px"});
		 }else{
			 $(".jumbotron").css({"min-height":"500px"});
		 }
	  }else{
		  $(".jumbotron").css({
			  "background-color":"#3dccac",
			  "background-image":"url("+host+"/www/skin/green/img/Artboard.svg)",
		  });
	  }
}
if(iscomputer){
	window.onscroll = function(){
	    var scrollTop = $(window).scrollTop();
	    if(scrollTop >= 200){
	        $("#menu").addClass("menu-default");
	       // $("#menu").removeClass(".navbar-nav li a");	
	    }
	    else{
	    	$("#menu").removeClass("menu-default");
	    	 
	    }
	}
}else{
	$("#menu").addClass("navbar-default");
}

$("#navbar li").each(function(){
	$(this).hover(function(){
	    //$(this).css({"background-color":"#e7e7e7"});
	    $(this).addClass("menu-hover-li");
	});
	$(this).mouseleave(function(){
		$(this).removeClass("menu-hover-li");
	});

});