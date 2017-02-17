package com.kingschan.blog.po;
// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.kingschan.blog.common.bean.convert.BeanConvert;


/**
 * 
*    
* 类名称：ArticleComment   
* 类描述：   
* 创建人：kings.chan
* 创建时间：2016-10-24 下午1:08:09   
* 修改人：   
* 修改时间：
* 项目：ROOT
* 修改备注：   
* @version    
*
 */
@Entity
@Table(name="blog_article_comment")
public class ArticleComment  implements java.io.Serializable,BeanConvert {


    // Fields    

	private static final long serialVersionUID = 1L;
	private String id;
     private Article article;
     private String CText;
     private Timestamp CDatetime;
     private User CUser;
     private String CWebsiteId;
     private User CTosomeone;
     private String CIsdel;
     private Integer CSupport;
     private String CRoot;
     private Integer CReplyTotal;
     private Set<ArticleCommentSupport> supports = new HashSet<ArticleCommentSupport>(0);

    // Constructors

    /** default constructor */
    public ArticleComment() {
    }

	/** minimal constructor */
    public ArticleComment(Article article, String CText, Timestamp CDatetime, User CUserId, String CWebsiteId) {
        this.article = article;
        this.CText = CText;
        this.CDatetime = CDatetime;
        this.CUser = CUserId;
        this.CWebsiteId = CWebsiteId;
    }
    
    /** full constructor */
    public ArticleComment(Article article, String CText, Timestamp CDatetime, User CUserId, String CWebsiteId,User CTosomeone) {
        this.article = article;
        this.CText = CText;
        this.CDatetime = CDatetime;
        this.CUser = CUserId;
        this.CWebsiteId = CWebsiteId;
        this.CTosomeone=CTosomeone;
    }

   
    // Property accessors
    @GenericGenerator(name="generator", strategy="uuid.hex")@Id @GeneratedValue(generator="generator")
    
    @Column(name="id", unique=true, nullable=false, length=32)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="c_article_id", nullable=false)

    public Article getArticle() {
        return this.article;
    }
    
    public void setArticle(Article article) {
        this.article = article;
    }
    
    @Column(name="c_text", nullable=false, length=1000)

    public String getCText() {
        return this.CText;
    }
    
    public void setCText(String CText) {
        this.CText = CText;
    }
    
    @Column(name="c_datetime", nullable=false, length=19)

    public Timestamp getCDatetime() {
        return this.CDatetime;
    }
    
    public void setCDatetime(Timestamp CDatetime) {
        this.CDatetime = CDatetime;
    }
    
//    @Column(name="c_user_id", nullable=false, length=32)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="c_user_id", nullable=false)
    public User getCUser() {
        return this.CUser;
    }
    
    public void setCUser(User CUserId) {
        this.CUser = CUserId;
    }
    
    @Column(name="c_website_id", nullable=false, length=32)
    public String getCWebsiteId() {
        return this.CWebsiteId;
    }
    
    public void setCWebsiteId(String CWebsiteId) {
        this.CWebsiteId = CWebsiteId;
    }
//    @Column(name="c_tosomeone", nullable=false, length=32)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="c_tosomeone")
	public User getCTosomeone() {
		return CTosomeone;
	}

	public void setCTosomeone(User cTosomeone) {
		CTosomeone = cTosomeone;
	}
    
	@Column(name = "c_isdel", nullable = false, length = 4)
	public String getCIsdel() {
		return this.CIsdel;
	}

	public void setCIsdel(String CIsdel) {
		this.CIsdel = CIsdel;
	}
	@Column(name="c_support", nullable=false)
	public Integer getCSupport() {
		return CSupport;
	}

	public void setCSupport(Integer cSupport) {
		CSupport = cSupport;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="articleComment")
	public Set<ArticleCommentSupport> getSupports() {
		return supports;
	}

	public void setSupports(Set<ArticleCommentSupport> supports) {
		this.supports = supports;
	}
	@Column(name = "c_root", nullable = false, length = 32)
	public String getCRoot() {
		return CRoot;
	}

	public void setCRoot(String cRoot) {
		CRoot = cRoot;
	}
	@Column(name="c_reply_total", nullable=false)
	public Integer getCReplyTotal() {
		return CReplyTotal;
	}

	public void setCReplyTotal(Integer cReplyTotal) {
		CReplyTotal = cReplyTotal;
	}

	@Override
	public <ArticleCommentVo> ArticleCommentVo po2vo(ArticleCommentVo obj) throws Exception {
		BeanUtils.copyProperties(this, obj);
		return obj;
	}
	
	
    
}