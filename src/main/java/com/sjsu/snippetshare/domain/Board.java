package com.sjsu.snippetshare.domain;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Board {
	private String boardId;
	private String boardName;
	private String boardOwner;
	private String category;
	private String privacy;
	private List<String> accessList = new ArrayList<String>();
	private List<Snippet> snippets = new ArrayList<Snippet>();

	public Board(String boardName, String boardOwner, String category, String privacy) {
		this.boardName = boardName;
		this.boardOwner = boardOwner;
		this.category = category;
		this.privacy = privacy;
	}

	public Board() {
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

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

	public List<String> getAccessList() {
		return accessList;
	}

	public void setAccessList(List<String> accessList) {
		this.accessList = accessList;
	}

	public List<Snippet> getSnippets() {
		return snippets;
	}

	public void setSnippets(List<Snippet> snippets) {
		this.snippets = snippets;
	}


	public Board makePOJOFromBSON(DBObject dbo) {
		String id = dbo.get("_id").toString();
		String name = dbo.get("Name").toString();
		String owner = dbo.get("Owner").toString();
		String privacy = dbo.get("Privacy").toString();
		String category = dbo.get("Category").toString();
		ArrayList<String> accessList = new ArrayList<String>();
		ArrayList<String> dbList = (ArrayList<String>) dbo.get("AccessList");
		if (dbList != null) {
			for (Iterator<String> iterator = dbList.iterator(); iterator.hasNext(); ) {
				String accessPerson = "";
				accessPerson =  iterator.next();
				accessList.add(accessPerson);
			}
		}

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
}
