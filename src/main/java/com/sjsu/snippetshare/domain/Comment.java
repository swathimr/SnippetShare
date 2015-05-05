package com.sjsu.snippetshare.domain;


/**
 * Created by mallika on 5/3/15.
 */

import org.bson.types.ObjectId;

public class Comment 
{
    private String commentId;
    private String text;
	private String ownerId;

    public Comment(String ownerId, String text) {
        this.ownerId = ownerId;
        this.text = text;
    }

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
}
