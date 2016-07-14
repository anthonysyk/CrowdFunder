package com.supinfo.supcrowdfunder.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Category implements Serializable {

	private int idCategory;
	private String libelle;

	public int getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}
