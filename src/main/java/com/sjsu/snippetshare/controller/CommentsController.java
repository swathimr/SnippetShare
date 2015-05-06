package com.sjsu.snippetshare.controller;

import java.net.UnknownHostException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sjsu.snippetshare.domain.Comment;
import com.sjsu.snippetshare.service.CommentsHandler;
public class CommentsController {
/*
	CommentsHandler handler;
	@RequestMapping(value="/comments/{user}/{board}/{snippet}",method=RequestMethod.PUT)
	public Comment createComment(@PathVariable("user") String user,@PathVariable("board") String board,
			@PathVariable("snippet") String snippet,@ModelAttribute Comment comment,Model model) 
					throws UnknownHostException
	{
		//System.out.println("yup in here"+user.getEmail());
		handler= new CommentsHandler();
		if(!comment.getText().equals(null))
		{
			comment = handler.createComment(comment,user,board,snippet);
		}
		
		return comment;
	}
	
	@RequestMapping(value="/comments/{id1}/{id2}",method=RequestMethod.PUT)
	public Comment updateComment(@PathVariable("id1") String snippetOwner,@PathVariable("id2") String comment_id,
			@ModelAttribute Comment comment,Model model) throws UnknownHostException
	{
		//System.out.println("yup in here"+user.getEmail());
		handler= new CommentsHandler();
		if(!comment.getText().equals(null))
		{
			comment = handler.updateComment(comment,snippetOwner,comment_id);
		}
		
		return comment;
	}
	
	
	@RequestMapping(value="/comments/{id1}/{id2}",method=RequestMethod.PUT)
	public Comment viewComment(@PathVariable("id1") String snippetOwner,@PathVariable("id2") String comment_id,
			@ModelAttribute Comment comment,Model model) throws UnknownHostException
	{
		//System.out.println("yup in here"+user.getEmail());
		handler= new CommentsHandler();
		comment = handler.viewComment(comment,snippetOwner,comment_id);
		
		
		return comment;
	}*/
	
}