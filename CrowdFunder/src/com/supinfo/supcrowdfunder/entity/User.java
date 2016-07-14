package com.supinfo.supcrowdfunder.entity;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("serial")
public class User  implements Serializable{

	private int idUser;
	private String name;
	private String firstName;
	private String password;
	private String mail;
	
	private Type type;
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String encryptPassword(String password) {
		String result = null;
		try{
			result = new String(MessageDigest.getInstance("SHA-1").digest(password.getBytes()));
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return result;
	}
}
