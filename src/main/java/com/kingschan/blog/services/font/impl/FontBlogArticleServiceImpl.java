package com.kingschan.blog.services.font.impl;

import java.util.List;

import com.kingschan.blog.services.system.impl.EmailNotifyServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kingschan.blog.model.vo.ArticleCommentVo;
import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.dao.font.BlogFontDao;
import com.kingschan.blog.dao.impl.ArticleDaoImpl;
import com.kingschan.blog.dao.impl.WebSiteDaoImpl;
import com.kingschan.blog.po.Article;
import com.kingschan.blog.po.ArticleComment;
import com.kingschan.blog.po.User;
import com.kingschan.blog.po.WebSite;
import com.kingschan.blog.services.font.FontBlogArticleService;
import com.kingschan.blog.services.impl.CommonServiceImpl;
import com.kingschan.blog.services.impl.UserServiceImpl;
import com.kingschan.blog.util.BlogUtil;
import com.kingschan.blog.util.TimeStampUtil;
@Service
@SuppressWarnings("unchecked")
public class FontBlogArticleServiceImpl extends CommonServiceImpl implements FontBlogArticleService {

	@Autowired
	private BlogFontDao fontBlogDao;
	@Qualifier("ArticleDaoImpl")
    @Autowired
    private ArticleDaoImpl article_dao;
	@Autowired
    private UserServiceImpl userServ;
    @Autowired
    private WebSiteDaoImpl websiteDao;
	@Autowired
	private EmailNotifyServiceImpl emailNotifyService;
	@Override
	public Pagination getArticleDiscuss(Integer page, Integer limit,
			String website, String articleId, String model,String currentUser) throws Exception {
		
		Pagination p = fontBlogDao.getArticleDiscuss(page, limit, articleId, currentUser, "", model);
		
		List<ArticleCommentVo> lis = (List<ArticleCommentVo>) p.getData();
		for (ArticleCommentVo item : lis) {
			if (item.getCReplyTotal()>0) {
				Pagination temp_p=fontBlogDao.getArticleDiscuss(1, 10, articleId, currentUser, item.getId(), "default");
				if (null!=temp_p.getData()&&temp_p.getData().size()>0) {
					p.put(item.getId(), temp_p.getData());
				}
				
			}
		}
		/*Pagination p = fontBlogDao.getArticleDiscuss(page, limit, articleId, model,currentUser);
			List<ArticleCommentVo> list = new ArrayList<ArticleCommentVo>();
			if (null!=p.getData()) {
				List<Object[]> lis =(List<Object[]>) p.getData();
				for (Object[] item : lis) {
					ArticleComment comments=(ArticleComment) item[0];
					User cuser=(User) item[1];
					User atUser=(User) item[2];
					ArticleCommentSupport support=(ArticleCommentSupport) item[3];
					
					ArticleCommentVo vo=comments.po2vo(new ArticleCommentVo());
					vo.setCUser(cuser.po2vo(new UserVo()));
					if (null!=atUser) {
						vo.setCTosomeone(atUser.po2vo(new UserVo()));
					}
					if (null!=support) {
						vo.setExistsSupport(true);
					}
					vo.setCText(vo.getCText().replaceAll("\\<(\\/)?p\\>", ""));
					list.add(vo);
					if (vo.getCReplyTotal()>0) {
						List<ArticleComment> replys =getArticleDiscussReplyById(1, 10, vo.getId(),currentUser);
						List<ArticleCommentVo> replys_list = new ArrayList<ArticleCommentVo>();
						for (ArticleComment reply : replys) {
							ArticleCommentVo vo1=reply.po2vo(new ArticleCommentVo());
							vo1.setCUser(reply.getCUser().po2vo(new UserVo()));
							if (null!=reply.getCTosomeone()) {
								vo1.setCTosomeone(reply.getCTosomeone().po2vo(new UserVo()));
							}
							if (null!=reply.getSupports()&&reply.getSupports().size()>0) {
								vo1.setExistsSupport(true);
							}
							vo1.setCText(vo1.getCText().replaceAll("\\<(\\/)?p\\>", ""));
							replys_list.add(vo1);
						}
						p.put(vo.getId(), replys_list);
					}
				}
			}
			p.setData(list);*/
			return p;
	}

	@Override
	public List<ArticleComment> getArticleDiscussReplyById(Integer page, Integer limit,
			String rootId,String currentUser) throws Exception {
		return fontBlogDao.getArticleDiscussReplyById(page, limit, rootId,currentUser);
	}

	@Override
	public void addArticleDiscuss(String userId, String tosomeOne,
			String articleid, String text, String root) throws Exception {
		//email
				String toEmail=null;
				String mailTitle=null;
				String mailContent=null;
				boolean canSendEmail=false;
				
				ArticleComment ac = new ArticleComment();
				Article a = article_dao.getArticleByID(articleid);
				WebSite web=websiteDao.getWebSite(a.getWebsiteid());
				//
				a.setArticleTotalComment(a.getArticleTotalComment()+1);
				ac.setArticle(a);
				//text
				String html=BlogUtil.markDownToHtml(text);
				String txt=Jsoup.clean(html, Whitelist.none()
						.addTags("h1").addTags("h2").addTags("h3").addTags("h4").addTags("h5")
						.addTags("strong").addTags("em").addTags("blockquote")
						.addTags("code").addTags("pre")
						.addTags("ul").addTags("ol").addTags("li")
						.addAttributes("img", "src","alt")
						.addAttributes("a", "href")
						);
				ac.setCText(txt);
				User u = userServ.getUser(userId);
				ac.setCUser(u);
				ac.setCWebsiteId(a.getWebsiteid());
				ac.setCDatetime(TimeStampUtil.getCurrentDate());
				String markdownhtml=BlogUtil.markDownToHtml(text);
				ac.setCRoot(root);
				ac.setCReplyTotal(0);
				User user=new User();
				if(null!=tosomeOne&&!tosomeOne.isEmpty()){
					user=userServ.getUser(tosomeOne);
					if (user.getUserEmailActivate()) {
						canSendEmail=true;
						toEmail=user.getUserEmail();
						mailTitle=String.format("%s@你了~", u.getUserScreenName());
						mailContent=String.format("%s<br><a href='http://51so.info/%s/entry/%s.html#commentForm'>点击查看详情</a>",markdownhtml,web.getWebsiteName(),articleid);
					}
				}else {
					user.setId("");
					if (a.getUser().getUserEmailActivate()) {
						canSendEmail=true;
						toEmail=a.getUser().getUserEmail();
						mailTitle=String.format("%s 刚刚评论你的文章了~", u.getUserScreenName());
						mailContent=String.format("%s<br><a href='http://51so.info/%s/entry/%s.html#commentForm'>点击查看详情</a>",markdownhtml,web.getWebsiteName(),articleid);
					}
				}
				ac.setCTosomeone(user);
				ac.setCIsdel("√");
				ac.setCSupport(0);
				article_dao.save(a);
				article_dao.save(ac);
				if (root.matches("\\w{32}")) {
					ArticleComment comment= (ArticleComment) article_dao.get(ArticleComment.class, root);
					comment.setCReplyTotal(comment.getCReplyTotal()+1);
					article_dao.update(comment);
				}
				if (canSendEmail&&!debug) {
					//emailNotifyService.sendEmail(toEmail,mailTitle,mailContent);
					emailNotifyService.sendEmailToUsersByText(text,mailTitle,mailContent);
				}
	}

	@Override
	public ArticleVo getNext(String datetime, String website) throws Exception {
		return fontBlogDao.getNext(datetime, website);
	}

	@Override
	public ArticleVo getPrevious(String datetime, String website)
			throws Exception {
		return fontBlogDao.getPrevious(datetime, website);
	}

	

}
