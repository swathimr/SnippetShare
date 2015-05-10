package com.sjsu.snippetshare.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.*;
import com.sjsu.snippetshare.domain.Snippet;
import org.bson.types.ObjectId;

import com.sjsu.snippetshare.domain.Comment;

public class CommentsHandler {
	DBCollection coll;
	BasicDBObject doc;
	public Comment createComment(Comment comment, String commentOwner, String snippet) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		boolean updated = false;
		String comment_id = new ObjectId().toString();
		comment.setCommentId(comment_id);
		comment.setOwnerId(commentOwner);
		String ownerName = getNameFromId(commentOwner);
		comment.setOwnerName(ownerName);
		BasicDBObject newComment = new BasicDBObject("commentId",comment.getCommentId()).
						append("text",comment.getText()).append("ownerId", comment.getOwnerId())
				.append("ownerName", comment.getOwnerName());
		DBObject updateQuery = new BasicDBObject("snippets.snippetId", snippet);

		BasicDBObject updateCommand =
				new BasicDBObject("$push", new BasicDBObject("snippets.$.comments", newComment));
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

	private String getNameFromId(String commentOwner) throws UnknownHostException

	{
		DBCollection collection = MongoFactory.getConnection().getCollection("User");
		BasicDBObject query = new BasicDBObject("_id",new ObjectId(commentOwner));
		DBObject dbObj = collection.findOne(query);
		if(dbObj != null)
		{
			String nameOwn = dbObj.get("name").toString();
			System.out.println("Got owner name : "+nameOwn + " ID is : " + commentOwner);
			return nameOwn;
		}
		else
		{
			throw new MongoException("This user does not exist");
		}

	}


	public List<Comment> getAllComments(String userId, String boardId, String snippetId) throws UnknownHostException {
		coll = MongoFactory.getConnection().getCollection("Board");
		DBObject findQuery = new BasicDBObject("_id", new ObjectId(boardId))
				.append("snippets.snippetId", snippetId);
		Comment comment = new Comment();
		List<Comment> comments = new ArrayList<Comment>();
		DBCursor dbComments = coll.find(findQuery);
		if (dbComments != null) {
			while (dbComments.hasNext()) {
				DBObject dbComment = dbComments.next();
				comment = new Comment();
				comment.setCommentId(dbComment.get("commentId").toString());
				comment.setText(dbComment.get("text").toString());
				comment.setOwnerId(dbComment.get("ownerId").toString());
				comments.add(comment);
			}
		}
		return comments;
	}

	public boolean deleteComment(String boardId, String snippetId, String commentId) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		DBObject deleteQuery = new BasicDBObject("_id", new ObjectId(boardId))
				.append("snippets.snippetId", snippetId);
		DBObject delete = new BasicDBObject("commentId", commentId);
		BasicDBObject deleteCommand = new BasicDBObject("$pull", new BasicDBObject("snippets.$.comments", delete));
		WriteResult deleteResult = coll.update(deleteQuery, deleteCommand);
		if(deleteResult.getN() !=0)
		{
			return true;
		}
		else
		{
			throw new MongoException(deleteQuery);
		}
	}

	public Comment updateComment(Comment comment, String snippetId,String commentId) throws UnknownHostException
	{

		coll = MongoFactory.getConnection().getCollection("Board");
		boolean updated = false;
		//BasicDBObject newComment = new BasicDBObject("text",comment.getText()).append("owner_id",comment.getOwnerID());
		BasicDBObject updateComment = new BasicDBObject("text",comment.getText());
		DBObject updateQuery = new BasicDBObject("snippets.snippetId", snippetId).
				append("snippets.comments.commentId", commentId);
		BasicDBObject updateCommand =
				new BasicDBObject("$push", new BasicDBObject("snippets.comments.$.text", updateComment));
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


