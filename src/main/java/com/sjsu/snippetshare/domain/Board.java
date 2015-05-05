package com.sjsu.snippetshare.domain;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;

public class Board 
{
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
<<<<<<< Updated upstream
	
=======

	public List<String> getAccessList() 
	{
		return accessList;
	}

	public void setAccessList(List<String> accessList) 
	{
		this.accessList = accessList;
	}

	public List<Snippet> getSnippets() {
		return snippets;
	}

	public void setSnippets(List<Snippet> snippets) {
		this.snippets = snippets;
	}
	
	public Board makePOJOFromBSON(DBObject dbo) 
	{
		String id = dbo.get("_id").toString();
	    String name = dbo.get("Name").toString();
	    String owner = dbo.get("Owner").toString();
	    String privacy = dbo.get("Privacy").toString();
	    String category = dbo.get("Category").toString();
	    ArrayList<String> accessList = new ArrayList<String>(); 
	    accessList.add(dbo.get("AccessList").toString());
//	    ArrayList<Snippet> snippetList = new ArrayList<Snippet>();
//	    Snippet snip = new Snippet();
//	    snip.
//	    snippetList.add(dbo.get("snippets").toString());
		this.boardId = id;
		this.boardName = name;
		this.boardOwner = owner;
		this.privacy = privacy;
		this.category = category;
		this.accessList = accessList;
        return this;
    }
>>>>>>> Stashed changes
}
