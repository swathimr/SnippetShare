package com.sjsu.snippetshare.service;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.sjsu.snippetshare.domain.Comment;

public class CommentsHandler {
	
	DBCollection coll; 
	BasicDBObject doc;
	public Comment createComment(Comment comment, String user, String board,String snippet) throws UnknownHostException 
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		boolean updated = false;
		String comment_id = new ObjectId().toString();
		comment.setCommentId(comment_id);
		BasicDBObject newComment = new BasicDBObject("commentId",comment.getCommentId()).
				append("text",comment.getText()).append("ownerId",comment.getOwnerId());
		DBObject updateQuery = new BasicDBObject("_id", user)
							.append("Snippets.snippetId", new ObjectId(snippet));
		BasicDBObject updateCommand =
				new BasicDBObject("$push", new BasicDBObject("Snippets.$.comments", newComment));
		WriteResult collDB = coll.update(updateQuery, updateCommand);
		System.out.println("collDB:"+collDB);
		if(collDB.getN() !=0)
		{
			updated = true;
		}
		else
		{
			throw new MongoException(updateQuery);
		}
		return comment;
	}
	
	public String convertToString(ObjectId id)
	{
		return id.toString();
	}
	
	public Comment updateComment(Comment comment, String snippetOwner,String comment_id) throws UnknownHostException 
	{
		
		coll = MongoFactory.getConnection().getCollection("Board");
		boolean updated = false;
		//BasicDBObject newComment = new BasicDBObject("text",comment.getText()).append("owner_id",comment.getOwnerID());
		BasicDBObject updateComment = new BasicDBObject("text",comment.getText());
		DBObject updateQuery = new BasicDBObject("Snippets.owner_id", new ObjectId(snippetOwner)).
				append("Snippets.comments.comment_id", new ObjectId(comment_id));
		BasicDBObject updateCommand =
				new BasicDBObject("$push", new BasicDBObject("Snippets.comments.$.text", updateComment));
		WriteResult collDB = coll.update(updateQuery, updateCommand);
		System.out.println("collDB:"+collDB);
		if(collDB.getN() !=0)
		{
			updated = true;
			return comment;
		}
		else
		{
			throw new MongoException(updateQuery);
		}
		
	}
	
	public Comment viewComment(Comment comment, String snippetOwner,String comment_id) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		
		
		return null;
	}

}
