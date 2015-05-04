package com.sjsu.snippetshare.service;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.sjsu.snippetshare.domain.User;

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
		System.out.println("Facebook user inserted into DB::"+userdoc);
	}
	
	public boolean checkIfUserExists(User user) throws UnknownHostException
	{
		coll = MongoFactory.getConnection().getCollection("User");
		BasicDBObject query = new BasicDBObject("email",user.getEmail().toString());
		System.out.println("collecion is::::"+coll);
		DBObject q1 = coll.findOne(query);
		System.out.println("fetched value is:"+q1);
		if(q1!=null)
		{
			return true;
		}
		else
		{
			return false;
		}	
	}

}
