package com.sjsu.snippetshare.service;

import com.mongodb.*;
import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.domain.Comment;
import com.sjsu.snippetshare.domain.Snippet;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mallika on 5/3/15.
 */
public class SnippetHandler {

    DBCollection coll;
    BasicDBObject doc;

    public Snippet addSnippet(String boardid, Snippet snippet) {
        try {
            coll = MongoFactory.getConnection().getCollection("Board");
        } catch (UnknownHostException uhe) {
            return null;
        }
        snippet.setSnippetId(convertObjectIdToString(new ObjectId()));
        BasicDBObject dbSnippet = createSnippetDBObject(snippet);
        ObjectId boardId = new ObjectId(boardid);
        DBObject updateQuery = new BasicDBObject("_id", boardId);
        BasicDBObject updateCommand = new BasicDBObject("$push", new BasicDBObject("snippets", dbSnippet));
        coll.update(updateQuery, updateCommand);
        Snippet newSnippet = new Snippet();
        newSnippet = newSnippet.makePOJOFromBSON(dbSnippet);
        return newSnippet;
    }

    private BasicDBObject createSnippetDBObject(Snippet snippet) {
        BasicDBObject dbSnippet = new BasicDBObject();
        dbSnippet.put("snippetId", snippet.getSnippetId());
        dbSnippet.put("snippetName", snippet.getSnippetName());
        dbSnippet.put("snippetText", snippet.getSnippetText());
        dbSnippet.put("ownerId", snippet.getOwnerId());
        dbSnippet.put("comments", snippet.getComments());
        return dbSnippet;
    }

    public Snippet getSnippet(String boardid, String snippetId) {
        try {
            coll = MongoFactory.getConnection().getCollection("Board");
        } catch (UnknownHostException uhe) {
            return null;
        }
        ObjectId boardId = new ObjectId(boardid);
        BasicDBObject dbo = new BasicDBObject("_id", boardId);
        DBObject dbBoard = coll.findOne(dbo);
        Snippet snippet = new Snippet();
        ArrayList<BasicDBObject> snippets = (ArrayList<BasicDBObject>) dbBoard.get("snippets");
        for (Iterator<BasicDBObject> iterator = snippets.iterator(); iterator.hasNext(); ) {
            BasicDBObject dbSnippet = (BasicDBObject) iterator.next();
            if (dbSnippet.get("snippetId").equals(snippetId)) {
                snippet = snippet.makePOJOFromBSON(dbSnippet);
            }
        }
        return snippet;
    }

    public Snippet updateSnippet(String boardid, Snippet snippet) {
        try {
            coll = MongoFactory.getConnection().getCollection("Board");
        } catch (UnknownHostException uhe) {
            return null;
        }
        ObjectId boardId = new ObjectId(boardid);
        BasicDBObject dbBoard = new BasicDBObject("_id", boardId);
        BasicDBObject snipId = new BasicDBObject("snippetId", snippet.getSnippetId());
        BasicDBObject snip = new BasicDBObject("snippets", snipId);
        BasicDBObject removeQuery = new BasicDBObject("$pull", snip);
        coll.update(dbBoard, removeQuery);
        BasicDBObject dbSnippet = createSnippetDBObject(snippet);
        DBObject updateQuery = new BasicDBObject("_id", boardId);
        BasicDBObject updateCommand = new BasicDBObject("$push", new BasicDBObject("snippets", dbSnippet));
        WriteResult collDB = coll.update(updateQuery, updateCommand);
        if(collDB.getN() == 0) {
            throw new MongoException(updateQuery);
        }
        Snippet newSnippet = new Snippet();
        newSnippet = newSnippet.makePOJOFromBSON(dbSnippet);
        return newSnippet;
    }

    private String convertObjectIdToString(ObjectId id) {
        return id.toString();
    }

    public boolean deleteSnippet(String boardid, String snippetId) {
        try {
            coll = MongoFactory.getConnection().getCollection("Board");
        } catch (UnknownHostException uhe) {
            return false;
        }
        ObjectId boardId = new ObjectId(boardid);
        BasicDBObject dbBoard = new BasicDBObject("_id", boardId);
        BasicDBObject snipId = new BasicDBObject("snippetId", snippetId);
        BasicDBObject snip = new BasicDBObject("snippets", snipId);
        BasicDBObject removeQuery = new BasicDBObject("$pull", snip);
        WriteResult collDB = coll.update(dbBoard, removeQuery);
        if(collDB.getN() == 0) {
            throw new MongoException(removeQuery);
        }
        return true;
    }

    public List<Snippet> getAllSnippets(String boardid) {
        try {
            coll = MongoFactory.getConnection().getCollection("Board");
        } catch (UnknownHostException uhe) {
            return null;
        }
        ObjectId boardId = new ObjectId(boardid);
        BasicDBObject dbo = new BasicDBObject("_id", boardId);
        DBObject dbBoard = coll.findOne(dbo);
        List<Snippet> snippets = new ArrayList<Snippet>();
        Snippet snippet = new Snippet();
        ArrayList<BasicDBObject> dbSnippets = (ArrayList<BasicDBObject>) dbBoard.get("snippets");
        for (Iterator<BasicDBObject> iterator = dbSnippets.iterator(); iterator.hasNext(); ) {
            snippet = new Snippet();
            BasicDBObject dbSnippet = (BasicDBObject) iterator.next();
            snippet = snippet.makePOJOFromBSON(dbSnippet);
            snippets.add(snippet);
        }
        return snippets;
    }

}
