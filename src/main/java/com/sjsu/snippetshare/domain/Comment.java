package com.sjsu.snippetshare.domain;


/**
 * Created by mallika on 5/3/15.
 */

import org.bson.types.ObjectId;

public class Comment 
{

	String text;
	String commentID;
	String ownerID;
	
	public String getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}
	public String getText() 
	{
		return text;
	}
	public void setText(String text) 
	{
		this.text = text;
	}
	public String getCommentID() 
	{
		return commentID;
	}
	public void setCommentID(String commentID) 
	{
		this.commentID = commentID;
	}
}
