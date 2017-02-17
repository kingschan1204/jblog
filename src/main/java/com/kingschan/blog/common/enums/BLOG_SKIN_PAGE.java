package com.kingschan.blog.common.enums;
/**
 * 博客模板页面
 * @author Administrator
 *
 */
public enum BLOG_SKIN_PAGE {
	
	PAGE_INDEX("page_index","skin/%s/font/index"),//博客首页
	PAGE_ARTICLE_INFO("page_article_info","skin/%s/font/article_info"),//文章详情
	PAGE_ARTICLE_PSW("page_article_psw","skin/%s/font/article_psw"),//输入密码查看博文
	PAGE_ARTICLE_LIS("page_article_lis","skin/%s/font/article_lis"),//博文目录
	PAGE_LABLE_LIS("page_lable_lis","skin/%s/font/lable_lis"),//热门标签
	PAGE_ARTICLE_TIMELINE("page_article_timeline","skin/%s/font/article_timeline"),//文章时间轴
	PAGE_BOOKMARK_LIS("page_bookmark_lis","skin/%s/font/bookmark_lis"),//书签目录
	PAGE_MSG_BOARD("page_msg_board","skin/%s/font/msg_board"),//留言板
	PAGE_MSG("page_msg_board","skin/%s/font/msg"),//消息提示
	PAGE_TIMELINE("page_timeline","skin/%s/font/timeline")//时间轴
	;
	private String key;
	private String path;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	private BLOG_SKIN_PAGE(String p_key,String p_path){
		this.key=p_key;
		this.path=p_path;
	}
}
