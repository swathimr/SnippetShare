package com.sjsu.snippetshare.domain;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class Board {
	private String boardId;
	private String boardName;
	private String boardOwner;
	private String category;
	private String privacy;
	private List<String> accessList =  new ArrayList<String>();
	public List<String> getAccessList() {
		return accessList;
	}
	private List<Snippet> snippets = new ArrayList<Snippet>();
	
	public String getBoardName() {
		return boardName;
	}
	public List<Snippet> getSnippets() {
		return snippets;
	}
	public void setSnippets(List<Snippet> snippets) {
		this.snippets = snippets;
	}
	public void setAccessList(List<String> accessList) {
		this.accessList = accessList;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
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
	
	public void addToAccessList(ObjectId user)
	{
		this.accessList.add(user.toString());
	}
	
}
