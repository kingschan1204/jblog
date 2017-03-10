package com.kingschan.blog.controller.pub;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kingschan.blog.common.enums.BLOG_SKIN_PAGE;
import com.kingschan.blog.common.freemarker.util.TemplateStaticUtil;
import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.model.vo.LableVo;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.po.WebSite;
import com.kingschan.blog.services.ArticleService;
import com.kingschan.blog.services.font.FontBlogArticleService;
import com.kingschan.blog.services.pub.PubBlogService;
import com.kingschan.blog.util.BlogUtil;

@Controller
public class IndexController {
    
    @Autowired
    private ArticleService article_serv;
    @Autowired
    private PubBlogService pubBlogServ;
    @Autowired
    private FontBlogArticleService fontBlogServ;
    //http://51so.info/kingschan/ 用户主页
    //http://51so.info/kingschan/entry/a03e8979-cc61-4922-9ba3-634de32ed50b.html 文章
    //http://51so.info/kingschan/category/数据库     文章类型
    //http://51so.info/kingschan/tags/java   标签
    //http://51so.info/kingschan/date/20160216 日期
    //http://51so.info/kingschan/feed/entries/atom 订阅
    //http://51so.info/kingschan/mediaresource/60ec8852-951a-4b7c-92d9-bcba35813336  文件
    //http://www.51so.info/sitemap.xml sitemap自动生成
    //http://51so.info/kingschan/?page=1 分页
    @Autowired
    private TemplateStaticUtil tsu;
    /**
     * 主页
     * @param request
     * @return
     */
    @RequestMapping("/home")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response){
    	/*CookieUtil cu = new CookieUtil(request, response);
    	for (Cookie coo : cu.getCookies()) {
			System.out.println(coo.getName()+"|"+coo.getValue());
		}*/
    	//首页静态化
    	/*String url=String.format("%s?%s", request.getRequestURL(),request.getQueryString());
    	String key =CommomEncrypt.MD5(url);
    	String filename=key.concat(".html");
    	System.out.println(url);
    	ModelAndView av =new ModelAndView();
        av.addObject("page", request.getParameter("page"));
    	if (!tsu.existsCache(filename)) {
    		Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", request.getParameter("page"));
			map.put(Variable.SESSION_BLOG_WEBSITE.getKey(), request.getSession().getAttribute(Variable.SESSION_BLOG_WEBSITE.getKey()));
			String path =new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_INDEX,true).concat(".html");//String.format("/WEB-INF/page/%s.html", new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_INDEX,true));
			tsu.crateHTML( map, path, filename);	
		}
        return "cache/"+key;*/
        
        
      String view=new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_INDEX,true);
      ModelAndView av =new ModelAndView(view);
      av.addObject("page", request.getParameter("page"));
      return av;
    }
    /**
     * 博文目录
     * @param request
     * @return
     */
    @RequestMapping("/article_lis")
    public ModelAndView articleLis(HttpServletRequest request,String categoryId){
        ModelAndView av =new ModelAndView(new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_ARTICLE_LIS,true));
        av.addObject("page", request.getParameter("page"));
        av.addObject("categoryId", categoryId);
        return av;
    }
    /**
     * 热门标签
     * @param request
     * @return
     */
    @RequestMapping("/lable_lis")
    public ModelAndView lableLis(HttpServletRequest request,String labName ){
        ModelAndView av =new ModelAndView(new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_LABLE_LIS,true));
        av.addObject("page", request.getParameter("page"));
        av.addObject("labName",labName);
        return av;
    }
    /**
     * 文章时间轴 按年月归档
     * @param request
     * @return
     */
    @RequestMapping("/article_timeline")
    public ModelAndView articleTimeline(HttpServletRequest request,Integer page,String date){
        ModelAndView av =new ModelAndView(new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_ARTICLE_TIMELINE,true));
        av.addObject("page", page);
        av.addObject("date", date);
        return av;
    }
    /**
     * 书签目录
     * @param request
     * @param folderId
     * @return
     */
    @RequestMapping("/bookmark_lis")
    public ModelAndView bookmarkLis(HttpServletRequest request,String folderId){
        ModelAndView av =new ModelAndView(new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_BOOKMARK_LIS,true));
        av.addObject("page", request.getParameter("page"));
        av.addObject("folderId", folderId);
        return av;
    }
    
   
    
  //http://51so.info/kingschan/entry/a03e8979-cc61-4922-9ba3-634de32ed50b.html 文章
    /**
     * 文章详情
     * @param request
     * @param psw
     * @param page
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping("/entry/*")
    public ModelAndView articleInfo(HttpServletRequest request,String psw,Integer page,String model,HttpServletResponse reponse)throws Exception{
        ModelAndView av =new ModelAndView();
        String url=request.getRequestURI();
        String[] s=url.split("/");
            String keyword=URLDecoder.decode(s[s.length-1], "utf-8");
            if (keyword.endsWith(".html")) {
                keyword=keyword.replace(".html", "");
            }
            BlogUtil blogutil=new BlogUtil(request);
            WebSite ws =blogutil.getCurrentFontWebSite();
            String userid=blogutil.isLogin()?blogutil.getCurrentUser().getId():"";
            int pageindex=null==page?1:page;
            String type=null!=model&&model.matches("default|hot|aboutme")?model:"default";
            ArticleVo article= article_serv.getArticle(keyword,blogutil.isRobot()?false:true,ws.getId());
            if (null!=article) {
            	if (article.getArticlePrivate()&&!userid.equals(article.getUser().getId())) {
					av.setViewName(blogutil.getTemplate(BLOG_SKIN_PAGE.PAGE_MSG,true));
					av.addObject("msg", "私密博文禁止访问!");
					return av;
				}
            	String dt=article.getArticleUpdatetime();
            	av.addObject("next", fontBlogServ.getNext(dt, article.getWebsiteid()));
            	av.addObject("pre", fontBlogServ.getPrevious(dt, article.getWebsiteid()));
            	if (article.getArticleEditor().equals("markdown")) {
            		String html=BlogUtil.markDownToHtml(article.getArticleContent());
					article.setArticleContent(html);
				}
            	List<String> nav_title = new ArrayList<String>();
            	String _html=article.getArticleEditor().equals("markdown")?article.getArticleContent():Jsoup.clean(article.getArticleContent(),Whitelist.relaxed());
            	Document doc = Jsoup.parse(_html);
        		Elements h= doc.select("h1,h2,h3,h4,h5");
        		//markdown <h1><a href="#标题1" name="标题1"></a>标题1</h1>
        		if (null!=h&&h.size()>0) {
        			for (int i = 0; i < h.size(); i++) {
    					Element ele= h.get(i);
                        if (ele.text().trim().isEmpty())continue;
    					if (article.getArticleEditor().equals("markdown")) {
    						nav_title.add(String.format("<a href=\"#%s\" class=\"article-nav\" %s >%s</a>", ele.child(0).attr("name"),ele.tagName(),ele.text()));
    					}else{
    						ele.attr("id", ele.text());
    						ele.append(String.format("<a href=\"#%s\"  name=\"%s\"></a>",ele.text(),ele.text()));
    						nav_title.add(String.format("<a href=\"#%s\" class=\"article-nav\" %s>%s</a>", ele.text(),ele.tagName(),ele.text()));
    					}
    				}
        			article.setArticleContent(doc.toString());
				}
        		if (null!=article.getLables()) {
        			StringBuffer sb = new StringBuffer();
					for (LableVo lab : article.getLables()) {
						sb.append(" ").append(lab.getLableName());
					}
					article.setArticleLableStr(sb.toString());
				}
        		av.addObject("nav_title", nav_title);
            	Pagination p =fontBlogServ.getArticleDiscuss(pageindex, 10, ws.getId(), article.getId(), type,userid);
                av.addObject("article_comment_lis", p.getData());
                av.addObject("page", p);
                //是否已经喜欢了这篇文章
                if (StringUtils.isNotEmpty(userid)) {
                	boolean overLike=null==article_serv.getArticleLike(userid, article.getId())?false:true;
                	av.addObject("like",overLike);
				}
			}
            av.addObject("keyword",keyword );
            av.addObject("article",article);
            av.addObject("model",type);
            if (null==article) {
            	reponse.sendError(404, "not found .");
				return null;
			}else if (null!=article&&null!=article.getArticlePassword()&&!article.getArticlePassword().isEmpty()) {
            	//登录了
            	if (blogutil.isLogin()&&blogutil.getCurrentUser().getId().equals(article.getUser().getId())) {
            		av.setViewName(blogutil.getTemplate(BLOG_SKIN_PAGE.PAGE_ARTICLE_INFO,true));
				}else if ((null!=request.getSession().getAttribute(article.getId()))|| (null!=psw&&article.getArticlePassword().equals(psw))) {
					//有输入密码
            		av.setViewName(blogutil.getTemplate(BLOG_SKIN_PAGE.PAGE_ARTICLE_INFO,true));
            		request.getSession().setAttribute(article.getId(), "");
				}else{
					if (null!=psw) {
						av.addObject("msg","密码错误!" );
					}
					av.setViewName(blogutil.getTemplate(BLOG_SKIN_PAGE.PAGE_ARTICLE_PSW,true));
				}
			}else{
				av.setViewName(blogutil.getTemplate(BLOG_SKIN_PAGE.PAGE_ARTICLE_INFO,true));
			}
        return av;
    }
    
    /**
     * 标签
     * @param request
     * @return
     */
	//http://51so.info/kingschan/tags/java
    @RequestMapping("/tags/*")
    public ModelAndView tag(HttpServletRequest request){
        ModelAndView av =new ModelAndView(new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_INDEX,true));
        String url=request.getRequestURI();
        av.addObject("url", url);
        av.addObject("page", request.getParameter("page"));
        String[] s=url.split("/");
        try {
            String keyword=URLDecoder.decode(s[s.length-1], "utf-8");
            av.addObject("tag",keyword );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return av;
    }
    
    
    /**
     *文章类型
     * @param request
     * @return
     */
  //http://51so.info/kingschan/category/数据库 
    @RequestMapping("/category/*")
    public ModelAndView category(HttpServletRequest request){
        ModelAndView av =new ModelAndView(new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_INDEX,true));
        String url=request.getRequestURI();
        av.addObject("url", url);
        av.addObject("page", request.getParameter("page"));
        String[] s=url.split("/");
        try {
            String category=URLDecoder.decode(s[s.length-1], "utf-8");
            av.addObject("category", category);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return av;
    }
    /**
     * 网站地图
     * @param request
     * @return
     */
    @RequestMapping("/sitemap.xml")
    public ModelAndView sitemap(HttpServletRequest request){
        ModelAndView av =new ModelAndView("/font/sitemap");
        String url=request.getRequestURI();
        av.addObject("url", url);
        return av;
    }
    
    
    //http://51so.info/kingschan/date/20160216 日期
    /**
     * 日期
     * @param request
     * @return
     */
    @RequestMapping("/date/{date}")
    public ModelAndView date(@PathVariable String date,HttpServletRequest request,HttpServletResponse reponse){
    	if (!date.matches("\\d{6,8}")) {
			try {
				reponse.sendError(404, "not found .");
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        ModelAndView av =new ModelAndView(new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_INDEX,true));
        av.addObject("page", request.getParameter("page"));
        av.addObject("date", date);
        return av;
    }
    /**
     * 全文检索
     * @param request
     * @return
     */
    @RequestMapping("/query")
    public ModelAndView query(HttpServletRequest request,String q){
        ModelAndView av =new ModelAndView(new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_INDEX,true));
        av.addObject("page", request.getParameter("page"));
        try {
            if (null!=q&&!q.isEmpty()) {
            	String kw=BlogUtil.filterXss(q);
                av.addObject("fulltext",kw);
                av.addObject("keyword",q);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return av;
    }
       
    /**
     * 博客时间轴
     * @param request
     * @param page
     * @return
     */
    @RequestMapping("/blog-timeline")
    public ModelAndView blogTimeline(HttpServletRequest request,String page){
        ModelAndView av =new ModelAndView(new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_TIMELINE,true));
        try {
        	BlogUtil bu = new BlogUtil(request);
        	String userid=bu.getCurrentFontWebSite().getUser().getId();
        	int _page=null!=page&&page.matches("\\d+")?Integer.valueOf(page):1;
        	Pagination p= pubBlogServ.blogTimeLine(30, _page, userid,bu.getCurrentFontWebSite().getWebsiteName());
        	av.addObject("page", p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return av;
    }
    /**
     * 留言板
     * @param request
     * @param page
     * @return
     */
    @RequestMapping("/blog-msgboard")
    public ModelAndView blogMsgBoard(HttpServletRequest request,String page){
        ModelAndView av =new ModelAndView(new BlogUtil(request).getTemplate(BLOG_SKIN_PAGE.PAGE_MSG_BOARD,true));
        try {
        	BlogUtil bu = new BlogUtil(request);
//        	String userid=bu.getCurrentFontWebSite().getUser().getId();
        	int _page=null!=page&&page.matches("\\d+")?Integer.valueOf(page):1;
        	Pagination p= pubBlogServ.blogMsgBoard(6, _page, bu.getCurrentFontWebSite().getId());
        	av.addObject("page", p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return av;
    }
  }
