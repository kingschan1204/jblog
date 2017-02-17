<footer class="site-footer">
	<div class="container">
		<div class="row">
			<div class="col-md-8 col-sm-6 col-xs-12">
				<p class="copyright-text"> Copyrights © 2016 All Rights Reserved by <a href="http://51so.info" target="weibo">51so.info</a> of ${website.user.userScreenName}&nbsp;|&nbsp; 粤ICP备15058321号-1 </p>
				<p class="copyright-text"><a href="${blogprefix}/sitemap.xml" target="blank">sitemap</a> 建站时间:${website.websiteDatetime} | 上次登录：${website.user.userLastlogin!""}</p>
				<div class="blog-footer">${website.websiteFooter!}</div>
				<a href="http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=udLQ197Kl9rR2Nf5yMiX2tbU" target="_blank" title="email站长" data-toggle="tooltip" data-placement="top"><img class="img-rounded" src="${webroot}/www/home/img/qqmail.png"/></a>
				&nbsp;<a href="http://tongji.baidu.com/web/welcome/ico?s=40d474c12b419648c4b4004d802a8254" target="_blank"><img class="img-rounded" src="${webroot}/www/home/img/tongji.png"/></a>
				&nbsp;<a href="http://webscan.360.cn/index/checkwebsite/url/51so.info"><img class="img-rounded" src="http://img.webscan.360.cn/status/pai/hash/b25412c9657bad6268af432c46bc9003/?size=74x27"/></a>
			</div>
			<div class="col-md-4 col-sm-6 col-xs-12">
				<p class="back-to-top">
					<a id="scroll-up"  href="#" onclick="$('body,html').animate({scrollTop:0},500);" style="position: fixed;bottom: 20px;right: 30px;z-index: 9999;">&nbsp;</a>
					<#if article??>
					<a id="gocomment" title="评论" href="javascript:$('body,html').animate({scrollTop:$('#commentDiv').offset().top + 'px'},500);" style="position: fixed;bottom: 90px;right: 30px;z-index: 9999;">&nbsp;</a>
					</#if>
				</p>
			</div>
		</div>
	</div>
</footer>
<script>
$(document).ready(function(){
	    $(document).off('click.bs.dropdown.data-api');
	    var $dropdownLi = $('li.dropdown');
	    $dropdownLi.mouseover(function() {
	        $(this).addClass('open');
	    }).mouseout(function() {
	        $(this).removeClass('open');
	    });
});
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?40d474c12b419648c4b4004d802a8254";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
NProgress.done();
</script>
