package com.supinfo.supcrowdfunder.entity;

import java.io.Serializable;



@SuppressWarnings("serial")

public class Project  implements Serializable{

	private int idProject;
	private String title;
	
	private String descript;
	
	
	private String descript2;
	
	
	private String urlImg;
	
	
	private double threshold;	
	
	
	private java.sql.Date creationDate;
	
	
	private java.sql.Date completionDate;
	
	
	private User user;
	
	
	private Category category;
	
	
	public int getIdProject() {
		return idProject;
	}
	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	
	
	public String getDescript2() {
		return descript2;
	}
	public void setDescript2(String descript2) {
		this.descript2 = descript2;
	}
	
	
	public String getUrlImg() {
		return urlImg;
	}
	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}
	
	
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	public java.sql.Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(java.sql.Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public java.sql.Date getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(java.sql.Date completionDate) {
		this.completionDate = completionDate;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}
