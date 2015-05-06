package com.sjsu.snippetshare.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.sjsu.snippetshare.domain.Board;
import org.springframework.stereotype.Component;

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
		createBoard.put("snippets", board.getSnippets());
		createBoard.put("AccessList",getAccessList(board));
		WriteResult res = coll.insert(createBoard);
		System.out.println("WriteResult : "+res.toString());
		System.out.println("Facebook user inserted into DB::"+createBoard);
	}
	
	private List<String> getAccessList(Board board) 
	{
		System.out.println("Inside getAccessList");
		List<String> accessList = board.getAccessList();
		List<String> accessUserList = new ArrayList<String>();
		for(String access : accessList)
		{
			if(access != null)
			{
				try 
				{
					String userID = getUserIDFromEmail(access);
					accessUserList.add(userID);
				} catch (Exception e) 
				{

					e.printStackTrace();
				}
			}
		}
		return accessUserList;
	}

	private String getUserIDFromEmail(String access) throws Exception 
	{
		System.out.println("Inside getUserIDFromEmail");
		DBCollection collection = MongoFactory.getConnection().getCollection("User");
		BasicDBObject query = new BasicDBObject("email",access);
		DBObject dbObj = collection.findOne(query);;
		if(dbObj != null)
		{
			 return (dbObj.get("_id").toString());
		}
		else
		{
			throw new MongoException("This user does not exist");
		}
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
	
	public ArrayList<Board> getAllBoards(String userId, String accessPermission) throws UnknownHostException
	{
		ArrayList<Board> boardList = new ArrayList<Board>();
		coll = MongoFactory.getConnection().getCollection("TestBoard");
		DBCursor cursor = coll.find();
		DBObject curObj;
		Board board;
		while (cursor.hasNext())
		{
			board = new Board();
			curObj = cursor.next();
			Board boardObj = board.makePOJOFromBSON(curObj);
			boardList.add(boardObj);
		}
			cursor.close();
			return boardList;
	}

	
	public void deleteBoard(String boardName) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		BasicDBObject delquery = new BasicDBObject();
		delquery.put("_id",new ObjectId(boardName));
		coll.remove(delquery);
	}
	
	public Board updateBoard(Board board) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		BasicDBObject searchQuery = new BasicDBObject("_id",new ObjectId(board.getBoardId()));
		System.out.println(board.getBoardId());
		BasicDBObject updatedDocument = new BasicDBObject();								
		updatedDocument.append("$set", new BasicDBObject().append("Name", board.getBoardId())
				.append("Owner", board.getBoardOwner())
				.append("Category", board.getCategory())
				.append("Privacy", board.getPrivacy()));
		System.out.println("new docs:::"+updatedDocument);
		coll.update(searchQuery, updatedDocument);
		return board;
	}
	
	public Board getOneBoard(String boardID) throws UnknownHostException 
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		//BasicDBObject query = new BasicDBObject("Owner",board.getBoardOwner());
		BasicDBObject query = new BasicDBObject("_id",new ObjectId(boardID));
		DBObject cursor = coll.findOne(query);
		Board board = new Board();
		if(cursor != null)
		{
			board.setBoardId(cursor.get("_id").toString());
			board.setBoardName(cursor.get("Name").toString());
			board.setBoardOwner(cursor.get("Owner").toString());
			ArrayList<String> access = new ArrayList<String>();
			access.add(cursor.get("AccessList").toString());
			board.setAccessList(access);
			board.setCategory(cursor.get("Category").toString());
			board.setPrivacy(cursor.get("Privacy").toString());
			System.out.println(board.getBoardName());
			System.out.println(board.getBoardOwner());
			System.out.println(board.getCategory());
			System.out.println(board.getPrivacy());
		}
		
		//board.setAccessList(accessList);
		return board;
		
	}
	
	public Board getBoardInfo(String board_id) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		//ObjectId objId = new ObjectId(board.getBoardId());
		ObjectId objId = new ObjectId(board_id);
		BasicDBObject searchQuery = new BasicDBObject("_id",objId);
		Board boardObj = new Board();
		DBObject cursor = coll.findOne(searchQuery);
		if(cursor != null)
		{
			boardObj.setBoardName(cursor.get("Name").toString());
			boardObj.setBoardOwner(cursor.get("Owner").toString());
			boardObj.setCategory(cursor.get("Category").toString());
			boardObj.setPrivacy(cursor.get("Privacy").toString());
			System.out.println(boardObj.getBoardName());
			System.out.println(boardObj.getBoardOwner());
			System.out.println(boardObj.getCategory());
			System.out.println(boardObj.getPrivacy());
		}
		return boardObj;
		
	}
	
	
}
