package com.kingschan.blog.po;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name="blog_article_comment_support"
)

public class ArticleCommentSupport  implements java.io.Serializable {


    // Fields    

	private static final long serialVersionUID = 1L;
	private String id;
     private ArticleComment articleComment;
     private String userid;
     private Timestamp createtime;


    // Constructors

    /** default constructor */
    public ArticleCommentSupport() {
    }

    
    /** full constructor */
    public ArticleCommentSupport(ArticleComment articleComment, String userid, Timestamp createtime) {
        this.articleComment = articleComment;
        this.userid = userid;
        this.createtime = createtime;
    }

   
    // Property accessors
    @GenericGenerator(name="generator", strategy="assigned")@Id @GeneratedValue(generator="generator")
    
    @Column(name="id", unique=true, nullable=false, length=32)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="coment_id", nullable=false)

    public ArticleComment getArticleComment() {
        return this.articleComment;
    }
    
    public void setArticleComment(ArticleComment articleComment) {
        this.articleComment = articleComment;
    }
    
    @Column(name="userid", nullable=false, length=32)

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    @Column(name="createtime", nullable=false, length=19)

    public Timestamp getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
   








}