package com.sjsu.snippetshare.service;

import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.sjsu.snippetshare.domain.*;

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

    public BasicDBObject createSnippetDBObject(Snippet snippet) {
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

    public ArrayList<ArrayList<Snippet>> getAllSnippets(String boardid, String userId) {
        try {
            coll = MongoFactory.getConnection().getCollection("Board");
        } catch (UnknownHostException uhe) {
            return null;
        }
        ObjectId boardId = new ObjectId(boardid);
        BasicDBObject dbo = new BasicDBObject("_id", boardId);
        DBObject dbBoard = coll.findOne(dbo);
        ArrayList<Snippet> snippets = new ArrayList<Snippet>();
        Snippet snippet = new Snippet();
        ArrayList<BasicDBObject> dbSnippets = new ArrayList<BasicDBObject>();
        ArrayList<ArrayList<Snippet>> masterList = new ArrayList<ArrayList<Snippet>>();
        try {
            dbSnippets = (ArrayList<BasicDBObject>) dbBoard.get("snippets");
        } catch (NullPointerException npe) {
            return masterList;
        }
        for (Iterator<BasicDBObject> iterator = dbSnippets.iterator(); iterator.hasNext(); ) {
            snippet = new Snippet();
            BasicDBObject dbSnippet = (BasicDBObject) iterator.next();
            snippet = snippet.makePOJOFromBSON(dbSnippet);
            snippets.add(snippet);

        }
        masterList.add(0, snippets);
        masterList.add(1, new ArrayList<Snippet>());
        return masterList;
    }

    public boolean updateAccessList(String boardId, ArrayList<String> userList) throws Exception
    //public boolean updateAccessList(String boardId, String userEmailAddr) throws Exception
    {
            System.out.println("updateAccessList......boardId : "+ boardId);
            coll = MongoFactory.getConnection().getCollection("Board");
            ObjectId bId = new ObjectId(boardId);
            DBObject dbo = new BasicDBObject("_id", bId);
            //DBObject update = new BasicDBObject("AccessList", getUserFromEmail(userList));
            BasicDBObject updateCommand =
                    new BasicDBObject("$push", new BasicDBObject("AccessList",
                            new BasicDBObject("$each", getUserFromEmail(userList))));
            WriteResult result = coll.update(dbo, updateCommand);
            if(result.getN() != 0) {
                return true;
            }
            else
            {
                throw new MongoException(updateCommand);
            }
    }

    public ArrayList<String> getUserFromEmail(ArrayList<String> userList) throws Exception
    {
        ArrayList<String> userID = new ArrayList<String>();

            DBCollection collection = MongoFactory.getConnection().getCollection("User");
            for(String userEmail : userList)
            {
                System.out.println("Inside getUserFromEmail: "+ userEmail);
                DBObject dbo = new BasicDBObject("email", userEmail);
                DBObject obj = collection.findOne(dbo);
                if(obj != null)
                {
                    System.out.println("Found email address, adding user id");
                    userID.add(obj.get("_id").toString());
                    //return obj.get("_id").toString();
                }
                else
                {
                    throw new Exception("Email address does not exist");
                }

            }

        return userID;
    }


}
