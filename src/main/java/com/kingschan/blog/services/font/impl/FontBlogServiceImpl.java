package com.kingschan.blog.services.font.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import com.kingschan.blog.services.system.impl.EmailNotifyServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingschan.blog.model.vo.ArticleCommentVo;
import com.kingschan.blog.dao.font.impl.BlogFontDaoImpl;
import com.kingschan.blog.dao.impl.ReportDaoImpl;
import com.kingschan.blog.po.BlogMsgBoard;
import com.kingschan.blog.po.User;
import com.kingschan.blog.services.font.FontBlogService;
import com.kingschan.blog.services.impl.CommonServiceImpl;
import com.kingschan.blog.services.impl.UserServiceImpl;
import com.kingschan.blog.util.BlogUtil;
import com.kingschan.blog.util.RegexUtil;
import com.kingschan.blog.util.TimeStampUtil;
@SuppressWarnings("unchecked")
@Service
public class FontBlogServiceImpl extends CommonServiceImpl implements FontBlogService {

	@Autowired
	private BlogFontDaoImpl fontDao;
	@Autowired
	private ReportDaoImpl repoartDao;
	@Autowired
	private UserServiceImpl userServ;
	@Autowired
	private EmailNotifyServiceImpl emailNotifyService;
	@Override
	public void addMsgBoard(String root, String at, String content,
			String currentUser, String website,String url) throws Exception {
		String text=content;
		User user =(User) fontDao.get(User.class, currentUser);
		BlogMsgBoard msg = new BlogMsgBoard();
		if (at.matches("\\w{32}")) {
			User u = userServ.getUser(at);
			msg.setMsgAt(u);
		}
		if (StringUtils.isEmpty(root)) {
			//不是回复的时候取@
			String target=RegexUtil.findStrByRegx(content, "\\@\\w{4,10}");
			if (!target.isEmpty()) {
				String[] users=target.replaceAll("\\@|\\:", "").split(",");
				User u = userServ.getUser(users[0]);
				if (null!=u) {
					text=text.replace(String.format("@%s", users[0]), "");
					msg.setMsgAt(u);
				}
			}
		}
		//text
		String html=BlogUtil.markDownToHtml(text);
		String s=Jsoup.clean(html, Whitelist.none()
				.addTags("h1").addTags("h2").addTags("h3").addTags("h4").addTags("h5")
				.addTags("strong").addTags("em").addTags("blockquote")
				.addTags("code").addTags("pre")
				.addTags("ul").addTags("ol").addTags("li")
				.addAttributes("img", "src","alt")
				.addAttributes("a", "href")
				);
		
		msg.setMsgCount(0);
		msg.setMsgDatetime(TimeStampUtil.getCurrentDate());
		msg.setMsgFlag("√");
		msg.setMsgLike(0);
		msg.setMsgRoot(root);
		msg.setMsgSendUser(user);
		msg.setMsgText(s.replaceAll("\\<(\\/)?p\\>", ""));
		msg.setWebsiteid(website);
		fontDao.addMsgBoard(msg);
		if (!debug) {
			User cuser=msg.getMsgSendUser();
			User atuser=msg.getMsgAt();
			if (cuser.getId()!=atuser.getId()&&atuser.getUserEmailActivate()) {
				emailNotifyService.sendEmail(atuser.getUserEmail(), String.format("%s刚刚%s", cuser.getUserName(),StringUtils.isEmpty(root)?"给你留言了":"在留言板@你了"
				), String.format("%s<br><a href='%s'>查看详情</a>", msg.getMsgText(),url));
			}
		}
	}
	@Override
	public JSONArray loadMsgBoardReply(String website,String id, Integer page)
			throws Exception {
		List<BlogMsgBoard> lis= repoartDao.getMsgBoardByrootId(website,id,page);
		JSONArray jsons = new JSONArray();
		for (BlogMsgBoard blogMsgBoard : lis) {
			JSONObject json = new JSONObject();
			json.put("cuser", blogMsgBoard.getMsgSendUser().getUserName());
			json.put("cuserId", blogMsgBoard.getMsgSendUser().getId());
			json.put("atuser", blogMsgBoard.getMsgAt().getUserName());
			json.put("text",blogMsgBoard.getMsgText().replaceAll("\\<(\\/)?p\\>", ""));
			json.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(blogMsgBoard.getMsgDatetime()));
			jsons.add(json);
		}
		return jsons;
	}
	@Override
	public List<ArticleCommentVo> loadArticleDiscussReply(String articleId,
			String root, String currentUser, Integer page) throws Exception {
		return (List<ArticleCommentVo>) fontDao.getArticleDiscuss(page, 10, articleId, currentUser, root, "default").getData();
	}
	
	
}
