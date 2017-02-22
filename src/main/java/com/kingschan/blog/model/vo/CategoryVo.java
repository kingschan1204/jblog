package com.kingschan.blog.model.vo;


public class CategoryVo {

	private String id;
    private String categoryName;
    private String categoryRemark;
    private Integer categoryPosition;
    private Integer total;
    
    public CategoryVo(){}
    public CategoryVo(String id,String name,Integer total){
    	this.id=id;
    	this.categoryName=name;
    	this.total=total;
    }
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryRemark() {
		return categoryRemark;
	}
	public void setCategoryRemark(String categoryRemark) {
		this.categoryRemark = categoryRemark;
	}
	public Integer getCategoryPosition() {
		return categoryPosition;
	}
	public void setCategoryPosition(Integer categoryPosition) {
		this.categoryPosition = categoryPosition;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
    
    
    
}
