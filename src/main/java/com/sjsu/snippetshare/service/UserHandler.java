package com.sjsu.snippetshare.service;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.domain.User;
import org.bson.types.ObjectId;

public class UserHandler {

	DBCollection coll;
	BasicDBObject doc;

	public void SignUpUser(User user) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("User");
		BasicDBObject userdoc = new BasicDBObject("name",user.getName()).append("email",user.getEmail());
		if(user.getPassword()!=null)
		{
			userdoc.append("password", user.getPassword());
		}
		coll.insert(userdoc);
		user.setId(userdoc.getString("_id").toString());
		System.out.println("Facebook user inserted into DB::"+userdoc);
	}

	public boolean checkIfUserExists(User user) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("User");
		BasicDBObject query = new BasicDBObject("email",user.getEmail().toString());
		System.out.println("collecion is::::" + coll);
		DBObject q1 = coll.findOne(query);
		System.out.println("fetched value is:"+q1);

		if(q1!=null)
		{
			user.setId(q1.get("_id").toString());
			return true;

		}
		else
		{
			return false;
		}
	}

	public User getUser(String userid) throws UnknownHostException {
		coll = MongoFactory.getConnection().getCollection("User");
		ObjectId userId = new ObjectId(userid);
		BasicDBObject dbo = new BasicDBObject("_id", userId);
		DBObject dbUser = coll.findOne(dbo);
		User newUser = new User();
		if (dbUser != null) {
			newUser.makePOJOFromBSON(dbUser);
		}
		return newUser;
	}

	public User updateUser(String userid, User user) throws UnknownHostException {
		coll = MongoFactory.getConnection().getCollection("User");
		ObjectId userId = new ObjectId(userid);
		DBObject newdbUser = new BasicDBObject()
				.append("name", user.getName())
				.append("email", user.getEmail())
				.append("password", user.getPassword());
		DBObject dbo = new BasicDBObject("_id", userId);
		DBObject updateQuery = new BasicDBObject("$set", newdbUser);
		coll.update(dbo, updateQuery);
		DBObject dbUser = coll.findOne(dbo);
		User updatedUser = new User();
		if (dbUser != null) {
			updatedUser.makePOJOFromBSON(dbUser);
		}
		return updatedUser;
	}

}