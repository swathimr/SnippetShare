package com.sjsu.snippetshare.service;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;

public class MongoFactory {

	public static DB getConnection() throws UnknownHostException 
	{
		MongoClientURI uri = new MongoClientURI("mongodb://cmpe275_team4:cmpe275_team4@ds053937.mongolab.com:53937/snippets");
	    MongoClient mongoClient = new MongoClient(uri);
	    DB db = mongoClient.getDB(uri.getDatabase());
	    mongoClient.setWriteConcern(WriteConcern.JOURNALED);
		return db;
	}	
}
