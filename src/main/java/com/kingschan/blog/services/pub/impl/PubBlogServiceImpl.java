package com.kingschan.blog.services.pub.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.model.vo.MsgBoardVo;
import com.kingschan.blog.model.vo.PubTimeLineVo;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.dao.font.impl.BlogFontDaoImpl;
import com.kingschan.blog.dao.impl.ReportDaoImpl;
import com.kingschan.blog.po.BlogMsgBoard;
import com.kingschan.blog.services.impl.CommonServiceImpl;
import com.kingschan.blog.services.pub.PubBlogService;
import com.kingschan.blog.util.BlogUtil;
@SuppressWarnings("unchecked")
@Service
public class PubBlogServiceImpl extends CommonServiceImpl implements PubBlogService {

//	private Logger log =LoggerFactory.getLogger(PubBlogServiceImpl.class);
	@Autowired
	private ReportDaoImpl reportDao;
	@Autowired
	private BlogFontDaoImpl fontDao;
	
	@Override
	public Pagination blogTimeLine(Integer limit, Integer page, String userid,String websitename)throws Exception {
		Pagination p =reportDao.blogTimeLine(limit, page, userid);
		List<PubTimeLineVo> lis =new ArrayList<PubTimeLineVo>();
		if (null!=p.getData()&&p.getData().size()>0) {
			String httproot=String.format("http://%s.%s", websitename,host);
			for (Object row : p.getData()) {
				Map<String, Object> map =(Map<String, Object>) row;
				String site=map.get("site").toString();
				PubTimeLineVo line =new PubTimeLineVo();
				String datstr=map.get("time").toString();
				line.setDate(datstr.substring(0, 10));
				line.setTime(datstr.substring(10,16));
				line.setType(map.get("type").toString());
				StringBuffer sb = new StringBuffer();
				if (line.getType().equals("article")) {
					sb.append(String.format("<h4><a href=\"%s/entry/%s.html\" target='_blank'>%s</a></h4>",httproot,map.get("id").toString(),map.get("title").toString()))
					.append(map.get("text").toString());
				}else if (line.getType().equals("comment")) {
					String html=BlogUtil.markDownToHtml(map.get("text").toString());
					html=BlogUtil.atUserFormat(html);
					sb.append("评论了文章:")
					.append(String.format("<a href=\"http://%s.%s/entry/%s.html\" target='_blank'>《%s》</a>",site,host,map.get("id").toString(),map.get("title").toString()))
					.append(html)
					;
				}else if (line.getType().equals("like")) {
					sb.append("喜欢了文章:")
					.append(String.format("<a href=\"http://%s.%s/entry/%s.html\" target='_blank'>《%s》</a>",site,host,map.get("id").toString(),map.get("title").toString()))
					;
				}else if (line.getType().equals("res")) {
					if (map.get("text").toString().contains("image")) {
						sb.append("上传图片:")
						.append(String.format("<img src=\"http://res.51so.info/%s_profile.200X200\" alt=\"%s\" title=\"%s\" class=\"img-thumbnail\"/>",
								map.get("id").toString(),
								map.get("title").toString(),
								map.get("title").toString()))
						;
					}else{
						sb.append("上传了文件:").append(map.get("title").toString());
					}
					
				}
				line.setContent(sb.toString());
				lis.add(line);
			}
		}
		p.setData(lis);
		return p;
	}

	@Override
	public Pagination blogMsgBoard(Integer limit, Integer page, String website)
			throws Exception {
		Pagination p = reportDao.blogMsgBoard(limit, page, website);
	/*	Object total= reportDao.uniqueQueryByHql("select count(*) from BlogMsgBoard where websiteid=? and msgFlag='√'", true, website);
		p.put("total", total);*/
		List<MsgBoardVo> list = new ArrayList<MsgBoardVo>();
		if (null!=p.getData()) {
			List<BlogMsgBoard> lis =(List<BlogMsgBoard>) p.getData();
			for (BlogMsgBoard item : lis) {
//				item.setMsgText(BlogUtil.atUserFormat(item.getMsgText()));
				MsgBoardVo vo=item.po2vo(new MsgBoardVo());
				vo.setMsgText(vo.getMsgText().replaceAll("\\<(\\/)?p\\>", ""));
				list.add(vo);
				if (item.getMsgCount()>0) {
					List<BlogMsgBoard> replys =reportDao.getMsgBoardByrootId(website, item.getId(),1);
					List<MsgBoardVo> replys_list = new ArrayList<MsgBoardVo>();
					for (BlogMsgBoard blogMsgBoard : replys) {
						MsgBoardVo vo1=blogMsgBoard.po2vo(new MsgBoardVo());
						replys_list.add(vo1);
						vo1.setMsgText(vo1.getMsgText().replaceAll("\\<(\\/)?p\\>", ""));
					}
					p.put(item.getId(), replys_list);
				}
			}
		}
		p.setData(list);
		return p;
	}

	

	@Override
	public List<ArticleVo> similarArticles(String website, String keyword,
			int limit) throws Exception {
		return fontDao.similarArticles(website, keyword, limit);
	}

	
}
