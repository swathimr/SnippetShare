package com.sjsu.snippetshare.domain;


/**
 * Created by mallika on 5/3/15.
 */

import com.mongodb.BasicDBObject;


public class Comment 
{
    private String commentId;
    private String text;
	private String ownerId;

    public Comment(String ownerId, String text) {
        this.ownerId = ownerId;
        this.text = text;
    }

    public Comment() {}

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Comment makePOJOFromBSON(BasicDBObject dbo) {
        this.commentId = (String) dbo.get("commentId");
        this.text = (String) dbo.get("text");
        this.ownerId = (String) dbo.get("ownerId");
        return this;
    }
}
