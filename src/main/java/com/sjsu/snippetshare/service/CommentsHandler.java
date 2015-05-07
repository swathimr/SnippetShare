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
		BasicDBObject newComment = new BasicDBObject("commentId",comment.getCommentId()).
				append("text",comment.getText()).append("ownerId",comment.getOwnerId());
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

	public boolean deleteComment(String boardId, String snippetId, String commentId) throws UnknownHostException {
		coll = MongoFactory.getConnection().getCollection("Board");
		DBObject deleteQuery = new BasicDBObject("_id", new ObjectId(boardId))
				.append("snippets.snippetId", snippetId);
		BasicDBObject deleteCommand = new BasicDBObject("$pull", new BasicDBObject("snippets.$.comments", commentId));
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

	/*
	DBCollection coll;
	BasicDBObject doc;

	public Comment addComment(String boardid, String snippetId, Comment comment) {
		try {
			coll = MongoFactory.getConnection().getCollection("Board");
		} catch (UnknownHostException uhe) {
			return null;
		}
		comment.setCommentId(convertObjectIdToString(new ObjectId()));
		BasicDBObject dbComment = createCommentDBObject(comment);
		ObjectId boardId = new ObjectId(boardid);
		BasicDBObject dbo = new BasicDBObject("_id", boardId);
		DBObject dbBoard = coll.findOne(dbo);
		Snippet newSnippet = new Snippet();
		ArrayList<BasicDBObject> dbSnippets = (ArrayList<BasicDBObject>) dbBoard.get("snippets");
		for (Iterator<BasicDBObject> iterator = dbSnippets.iterator(); iterator.hasNext(); ) {
			BasicDBObject dbSnippet = (BasicDBObject) iterator.next();
			if (dbSnippet.get("snippetId").equals(snippetId)) {
				newSnippet = newSnippet.makePOJOFromBSON(dbSnippet);
				break;
			}
		}
		newSnippet.addComment(comment);
		SnippetHandler snippetHandler = new SnippetHandler();
		snippetHandler.updateSnippet(boardid, newSnippet);
		Comment newComment = new Comment();
		newComment = newComment.makePOJOFromBSON(dbComment);
		return newComment;
	}

	private BasicDBObject createCommentDBObject(Comment comment) {
		BasicDBObject dbComment = new BasicDBObject();
		dbComment.put("commentId", comment.getCommentId());
		dbComment.put("text", comment.getText());
		dbComment.put("ownerId", comment.getOwnerId());
		return dbComment;
	}

	public Comment getComment(String boardid, String snippetId, String commentId) {
		return null;
	}

	public Comment updateComment(String boardid, String snippetId, Comment comment) {
		return null;
	}

	public boolean deleteComment(String boardid, String snippetId, String commentId) {
		return false;
	}

	public List<Comment> getAllComments(String boardid, String snippetId) {
		return null;
	}

	private String convertObjectIdToString(ObjectId id) {
		return id.toString();
	}

}

*/



















	/*
	DBCollection coll; 
	BasicDBObject doc;
	public Comment createComment(Comment comment, String commentOwner, String board,String snippet) throws UnknownHostException 
	{
		coll = MongoFactory.getConnection().getCollection("Board");
		boolean updated = false;
		String comment_id = new ObjectId().toString();
		comment.setCommentId(comment_id);
		comment.setOwnerId(commentOwner);
		BasicDBObject newComment = new BasicDBObject("commentId",comment.getCommentId()).
				append("text",comment.getText()).append("ownerId",comment.getOwnerId());
		DBObject updateQuery = new BasicDBObject("_id", new ObjectId(board))
							.append("snippets.snippetId", snippet);
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
	
	public Comment updateComment(Comment comment, String snippetId,String commentId) throws UnknownHostException 
	{
		
		coll = MongoFactory.getConnection().getCollection("Board");
		boolean updated = false;
		//BasicDBObject newComment = new BasicDBObject("text",comment.getText()).append("owner_id",comment.getOwnerID());
		BasicDBObject updateComment = new BasicDBObject("text",comment.getText());
		DBObject updateQuery = new BasicDBObject("snippets.snippetId", snippetId).
				append("snippets.comments.commentId", commentId);
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
	*/


