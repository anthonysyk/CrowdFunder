package com.supinfo.supcrowdfunder.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Contribution implements Serializable{
	

	private int idcontrib;
	

	private double montant;

	private java.sql.Date dateContribut;
	

	private User user;
	
	
	private Project project;
	

	public int getIdcontrib() {
		return idcontrib;
	}
	public void setIdcontrib(int idcontrib) {
		this.idcontrib = idcontrib;
	}

	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	public java.sql.Date getateContribut() {
		return dateContribut;
	}
	public void setDateContribut(java.sql.Date dateContribut) {
		this.dateContribut = dateContribut;
	}
}
