package com.kingschan.blog.po;
// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name="blog_res")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class BlogRes  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
     private String resName;
     private String resType;
     private String resKey;
     private String resHash;
     private String resWebsiteid;
     private Timestamp resDate;
     private String resCreator;
     private Long resSize;


    // Constructors

    /** default constructor */
    public BlogRes() {
    }

    
    /** full constructor */
    public BlogRes(String resName, String resType, String resKey, String resHash, String resWebsiteid, Timestamp resDate, String resCreator, Long resSize) {
        this.resName = resName;
        this.resType = resType;
        this.resKey = resKey;
        this.resHash = resHash;
        this.resWebsiteid = resWebsiteid;
        this.resDate = resDate;
        this.resCreator = resCreator;
        this.resSize = resSize;
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
    
    @Column(name="res_name", nullable=false, length=200)

    public String getResName() {
        return this.resName;
    }
    
    public void setResName(String resName) {
        this.resName = resName;
    }
    
    @Column(name="res_type", nullable=false, length=200)

    public String getResType() {
        return this.resType;
    }
    
    public void setResType(String resType) {
        this.resType = resType;
    }
    
    @Column(name="res_key", nullable=false, length=50)

    public String getResKey() {
        return this.resKey;
    }
    
    public void setResKey(String resKey) {
        this.resKey = resKey;
    }
    
    @Column(name="res_hash", nullable=false, length=50)

    public String getResHash() {
        return this.resHash;
    }
    
    public void setResHash(String resHash) {
        this.resHash = resHash;
    }
    
    @Column(name="res_websiteid", nullable=false, length=32)

    public String getResWebsiteid() {
        return this.resWebsiteid;
    }
    
    public void setResWebsiteid(String resWebsiteid) {
        this.resWebsiteid = resWebsiteid;
    }
    
    @Column(name="res_date", nullable=false, length=19)

    public Timestamp getResDate() {
        return this.resDate;
    }
    
    public void setResDate(Timestamp resDate) {
        this.resDate = resDate;
    }
    
    @Column(name="res_creator", nullable=false, length=32)

    public String getResCreator() {
        return this.resCreator;
    }
    
    public void setResCreator(String resCreator) {
        this.resCreator = resCreator;
    }
    
    @Column(name="res_size", nullable=false)

    public Long getResSize() {
        return this.resSize;
    }
    
    public void setResSize(Long resSize) {
        this.resSize = resSize;
    }
   








}