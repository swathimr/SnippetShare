package com.sjsu.snippetshare.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.DBObject;

public class User {

	public String id;
	String email;
	String password;
	String name;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public User makePOJOFromBSON(DBObject dbo) {
		this.setId(dbo.get("_id").toString());
		this.setName(dbo.get("name").toString());
		this.setEmail(dbo.get("email").toString());
		this.setPassword(dbo.get("password").toString());
		return this;
	}

}
