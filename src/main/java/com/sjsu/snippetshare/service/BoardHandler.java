package com.sjsu.snippetshare.service;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sjsu.snippetshare.domain.Board;
public class BoardHandler {

	DBCollection coll; 
	BasicDBObject doc;
	
	public void createBoard(Board board) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		BasicDBObject createBoard = new BasicDBObject();
		System.out.println(board.getBoardName());
		createBoard.put("Name",board.getBoardName());
		createBoard.put("Owner",board.getBoardOwner());
		createBoard.put("Category",board.getCategory());
		createBoard.put("Privacy",board.getPrivacy());
		createBoard.put("AccessList",board.getAccessList());
		coll.insert(createBoard);
		System.out.println("Facebook user inserted into DB::"+createBoard);
	}
	
	public void getBoard() throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		BasicDBObject query = new BasicDBObject("Owner","swathi6489@gmail.com");
		DBObject q1 = coll.findOne(query);
		System.out.println("fetched value is:"+q1);
		if(q1!=null)
		{
			//return
		}
	}
	
	public ArrayList<Board> getAllBoards(String boardOwn) throws UnknownHostException
	{

		ArrayList<Board> boardList = new ArrayList<Board>();
		coll = MongoFactory.getConnection().getCollection("Board");
		BasicDBObject query = new BasicDBObject("Owner",boardOwn);
		DBCursor cursor = coll.find(query);
		DBObject curObj;
		Board board;
		while (cursor.hasNext()) 
		{
			board = new Board();
		    curObj = cursor.next();
		    String id = curObj.get("_id").toString();
		    String name = curObj.get("Name").toString();
		    board.setBoardId(id);
		    board.setBoardName(name);
		    System.out.println("ID:"+id);
		    System.out.println("Name:"+name);
		    boardList.add(board);
		}
		
		cursor.close();
		
		return boardList;
	}
	
	public void deleteBoard(String boardName) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		BasicDBObject delquery = new BasicDBObject();
		delquery.put("Name",boardName);
		delquery.put("Owner", "swathi6489@gmail.com");
		coll.remove(delquery);
	}
	
	public void updateBoard(Board board) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		BasicDBObject searchQuery = new BasicDBObject();
		System.out.println(board.getBoardName());
		searchQuery.put("Name",board.getBoardName());
		searchQuery.put("Owner","swathi6489@gmail.com");
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("Name", "newBoardName"));
		System.out.println("new docs:::"+newDocument);
		coll.update(searchQuery, newDocument);
	}
	
	
}
