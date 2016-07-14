package com.supinfo.supcrowdfunder.entity;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Type implements Serializable {
	

	private int idType;
	private String libelleType;
	public int getIdType() {
		return idType;
	}
	public void setIdType(int idType) {
		this.idType = idType;
	}
	public String getLibelle() {
		return libelleType;
	}
	public void setLibelle(String libelle) {
		this.libelleType = libelle;
	}

}
