package com.sjsu.snippetshare.domain;

public class Board {
	private String boardName;
	private String boardOwner;
	private String category;
	private String privacy;
	private String accessList;
	
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getBoardOwner() {
		return boardOwner;
	}
	public void setBoardOwner(String boardOwner) {
		this.boardOwner = boardOwner;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public String getAccessList() {
		return accessList;
	}
	public void setAccessList(String accessList) {
		this.accessList = accessList;
	}
}
