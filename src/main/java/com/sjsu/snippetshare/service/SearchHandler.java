package com.sjsu.snippetshare.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.domain.Comment;
import com.sjsu.snippetshare.domain.Snippet;
import com.sjsu.snippetshare.domain.User;

public class SearchHandler {
	DBObject doc;
	DBObject query;
	DBObject a = new BasicDBObject();
	Board b;
	Snippet s;
	Comment c;
	User u;
	ArrayList<Comment> commentList = new ArrayList<Comment>();
	ArrayList<Snippet> snippetList = new ArrayList<Snippet>();
	ArrayList<User> userList = new ArrayList<User>();
	ArrayList<Board> boardList = new ArrayList<Board>();
	public ArrayList searchSnippets(String snippet) {
		try {
			c = new Comment();
			DBCollection board = MongoFactory.getConnection().getCollection(
					"Board");
			BasicDBObject query = new BasicDBObject();
			Pattern pattern = Pattern.compile(".*" + snippet + ".*");
			query.put("snippets.snippetText", pattern);
			DBCursor docs = board.find(query);
			System.out.println(docs);
			while (docs.hasNext()) {
				a = docs.next();
				BasicDBList dbSnippets = (BasicDBList) a.get("snippets");
				for (int i = 0; i < dbSnippets.size(); i++) {
					s = new Snippet();
					BasicDBObject snippetObj = (BasicDBObject) dbSnippets
							.get(i);
					BasicDBList dbComments = (BasicDBList) snippetObj
							.get("comments");
					for (int j = 0; j < dbComments.size(); j++) {
						BasicDBObject commentObj = (BasicDBObject) dbComments
								.get(j);
						String ctext = commentObj.get("text").toString();
						String coid = commentObj.get("owner_id").toString();
						c.setText(ctext);
						c.setOwnerId(coid);
						commentList.add(c);
					}
					String snippetId = snippetObj.get("snippetId").toString();
					String snippetName = snippetObj.get("snippetName")
							.toString();
					String snippetText = snippetObj.get("snippetText")
							.toString();
					s.setSnippetName(snippetName);
					s.setOwnerId(snippetId);
					s.setSnippetText(snippetText);
					s.setComments(commentList);
					snippetList.add(s);
				}
			}
			return snippetList;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public ArrayList searchBoards(String boards) {
		try {
			DBCollection board = MongoFactory.getConnection().getCollection(
					"Board");
			BasicDBObject query = new BasicDBObject();
			Pattern pattern = Pattern.compile(".*" + boards + ".*");
			query.put("Name", pattern);
			DBCursor docs = board.find(query);
			while (docs.hasNext()) {
				a = docs.next();
				b = new Board();
				String bid = a.get("_id").toString();
				String name = a.get("Name").toString();
				String owner = a.get("Owner").toString();
				String category = a.get("Category").toString();
				String privacy = a.get("Privacy").toString();
				b.setBoardId(bid);
				b.setBoardName(name);
				b.setBoardOwner(owner);
				b.setCategory(category);
				b.setPrivacy(privacy);
				boardList.add(b);
			}
			return boardList;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public ArrayList searchUsers(String users) {
		try {
			DBCollection board = MongoFactory.getConnection().getCollection(
					"User");
			BasicDBObject query = new BasicDBObject();
			Pattern pattern = Pattern.compile(".*" + users + ".*");
			System.out.println(pattern);
			query.put("email", pattern);
			DBCursor docs = board.find(query);
			System.out.println(docs);
			while (docs.hasNext()) {
				a = docs.next();
				u = new User();
				String uid = a.get("_id").toString();
				String uname = a.get("name").toString();
				String uemail = a.get("email").toString();
				u.setId(uid);
				u.setName(uname);
				u.setEmail(uemail);
				userList.add(u);
			}
			return userList;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
