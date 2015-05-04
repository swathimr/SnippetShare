package com.sjsu.snippetshare.service;

import com.mongodb.*;
import com.sjsu.snippetshare.db.SnippetDAO;
import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.domain.Comment;
import com.sjsu.snippetshare.domain.Snippet;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by mallika on 5/3/15.
 */
public class SnippetHandler {

    DBCollection coll;
    BasicDBObject doc;

    public Snippet createSnippet(Snippet snippet, String boardId) {
        try {
            coll = MongoFactory.getConnection().getCollection("Snippet");
        } catch (UnknownHostException uhe) {
            return null;
        }
        BasicDBObject dbSnippet = new BasicDBObject();
        snippet.setSnippetId(convertObjectIdToString(new ObjectId()));
        dbSnippet = createSnippetDBObject(snippet, dbSnippet);
        DBObject updateQuery = new BasicDBObject("boardId", boardId);
        BasicDBObject updateCommand = new BasicDBObject("$push", new BasicDBObject("snippets", dbSnippet));
        WriteResult collDB = coll.update(updateQuery, updateCommand);
        if(collDB.getN() == 0) {
            throw new MongoException(updateQuery);
        }
        return snippet;
    }

    private BasicDBObject createSnippetDBObject(Snippet snippet, BasicDBObject dbSnippet) {
        dbSnippet.put("snippetId", snippet.getSnippetId());
        dbSnippet.put("snippetName", snippet.getSnippetName());
        dbSnippet.put("snippetText", snippet.getSnippetText());
        dbSnippet.put("ownerId", snippet.getOwnerId());
        dbSnippet.put("comments", snippet.getComments());
        return dbSnippet;
    }

    public Snippet getSnippet(String boardId, String snippetId) {
        try {
            coll = MongoFactory.getConnection().getCollection("Snippet");
        } catch (UnknownHostException uhe) {
            return null;
        }
        DBObject findQuery = new BasicDBObject("boardId", boardId);
        

        DBObject dbSnippet = coll.findOne(findQuery);
        Snippet snippet = new Snippet();
        snippet.setSnippetId((String) dbSnippet.get("snippetId"));
        snippet.setSnippetName((String) dbSnippet.get("snippetName"));
        snippet.setSnippetText((String) dbSnippet.get("snippetText"));
        snippet.setOwnerId((String) dbSnippet.get("ownerId"));
        BasicDBList dbComments = new BasicDBList();

    }

    private String convertObjectIdToString(ObjectId id) {
        return id.toString();
    }

}
