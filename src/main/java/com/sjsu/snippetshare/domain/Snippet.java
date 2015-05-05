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
    private List<Comment> comments = new ArrayList<Comment>();

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

    public void setComments(List<Comment> comments) {
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
        this.snippetId = (String) dbo.get("snippetId");
        this.snippetName = (String) dbo.get("snippetName");
        this.snippetText = (String) dbo.get("snippetText");
        this.comments = (List<Comment>) dbo.get("comments");
        return this;
    }
}

