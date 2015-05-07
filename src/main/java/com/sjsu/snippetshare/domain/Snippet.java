package com.sjsu.snippetshare.domain;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;

/**
 * Created by mallika on 5/3/15.
 */
public class Snippet {

    private String snippetId;
    private String snippetName;
    private String ownerId;
    private String snippetText;
    private String boardId;
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    public Snippet(String snippetName, String ownerId, String snippetText) {
        this.snippetName = snippetName;
        this.ownerId = ownerId;
        this.snippetText = snippetText;
    }

    public Snippet() {}

    public String getSnippetName() {
        return snippetName;
    }

    public void setSnippetName(String snippetName) {
        this.snippetName = snippetName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getSnippetText() {
        return snippetText;
    }

    public void setSnippetText(String snippetText) {
        this.snippetText = snippetText;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public String getSnippetId() {
        return snippetId;
    }

    public void setSnippetId(String snippetId) {
        this.snippetId = snippetId;
    }

    public Snippet makePOJOFromBSON(DBObject dbo) {
        this.snippetId = dbo.get("snippetId").toString();
        this.snippetName =dbo.get("snippetName").toString();
        this.snippetText =dbo.get("snippetText").toString();
        this.comments = (ArrayList<Comment>) dbo.get("comments");
        return this;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}

