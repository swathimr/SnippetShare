package com.sjsu.snippetshare.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.sjsu.snippetshare.domain.Board;
public class BoardHandler {

	DBCollection coll; 
	BasicDBObject doc;
	
	public void createBoard(Board board) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		BasicDBObject createBoard = new BasicDBObject();
		;
		System.out.println(board.getBoardName());
		createBoard.put("Name",board.getBoardName());
		createBoard.put("Owner",board.getBoardOwner());
		createBoard.put("Category",board.getCategory());
		createBoard.put("Privacy",board.getPrivacy());
<<<<<<< Updated upstream
		createBoard.put("AccessList",board.getAccessList());
=======
		createBoard.put("snippets", board.getSnippets());
		createBoard.put("AccessList",getAccessList(board));
>>>>>>> Stashed changes
		coll.insert(createBoard);
		System.out.println("Facebook user inserted into DB::"+createBoard);
	}
	
	private List<String> getAccessList(Board board) 
	{
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
		coll = MongoFactory.getConnection().getCollection("User");
		BasicDBObject query = new BasicDBObject("email",access);
		DBObject dbObj = coll.findOne(query);;
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
	
	public ArrayList<Board> getAllBoards(String boardOwn) throws UnknownHostException
	{
<<<<<<< Updated upstream
		
		JSONObject obj = new org.json.JSONObject();
		JSONArray array = new JSONArray();
=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
		    String id = curObj.get("_id").toString();
		    String name = curObj.get("Name").toString();
		    board.setBoardId(id);
		    board.setBoardName(name);
		    System.out.println("ID:"+id);
		    System.out.println("Name:"+name);
		    boardList.add(board);
//		    obj.put("id", curObj.get("_id").toString());
//		    obj.put("boardName", curObj.get("Name").toString());
		    //array.put(obj);
		    //boardMap.put(curObj.get("_id").toString(),curObj.get("Name").toString());
=======
		    Board boardObj = board.makePOJOFromBSON(curObj);
		    boardList.add(boardObj);
>>>>>>> Stashed changes
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
