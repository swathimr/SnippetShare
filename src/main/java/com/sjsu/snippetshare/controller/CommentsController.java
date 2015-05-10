package com.sjsu.snippetshare.controller;

import java.net.UnknownHostException;
import java.util.List;

import com.sun.deploy.net.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sjsu.snippetshare.domain.Comment;
import com.sjsu.snippetshare.service.CommentsHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentsController {
	CommentsHandler handler;
	@RequestMapping(value="/createComment/{userId}/{boardId}/{snippetId}",method=RequestMethod.POST)
	public String createComment(@PathVariable("userId") String userId,@PathVariable("boardId") String boardId,
								@PathVariable("snippetId") String snippetId,@ModelAttribute Comment comment,
								RedirectAttributes redirectAttribute, Model model)
			throws UnknownHostException
	//public String createComment(@ModelAttribute Comment comment,
	//RedirectAttributes redirectAttribute,User user,Snippet snippet)
	//throws UnknownHostException
	{
		System.out.println("yup in here::"+userId +"snippet:"+snippetId);
		handler= new CommentsHandler();
		System.out.println(comment.getText());
		if(!comment.getText().equals(null))
		{
			comment = handler.createComment(comment,userId,snippetId);
		}

		redirectAttribute.addFlashAttribute("userId",userId);
		redirectAttribute.addFlashAttribute("boardId", boardId);
		return "redirect:/getAllSnippets/"+userId+"/"+boardId;

	}

	@RequestMapping(value="/updateComment/{board}/{snippet}/{comment}",method=RequestMethod.PUT)
	public Comment updateComment(@PathVariable("snippet") String snippetId,
								 @PathVariable("comment") String commentId,@ModelAttribute Comment comment,Model model) throws UnknownHostException
	{
		//System.out.println("yup in here"+user.getEmail());
		handler= new CommentsHandler();
		if(!comment.getText().equals(null))
		{
			comment = handler.updateComment(comment,snippetId,commentId);
		}

		return comment;
	}

	@RequestMapping(value = "/deleteComment/{boardId}/{snippetId}/{commentId}/{userId}")
	public String deleteComment(@PathVariable("boardId") String boardId,
								@PathVariable("snippetId") String snippetId,
								@PathVariable("commentId") String commentId
							, @PathVariable("userId") String userId) throws UnknownHostException {
		handler= new CommentsHandler();
		handler.deleteComment(boardId, snippetId, commentId);
		return "redirect:/getAllSnippets/"+userId+"/"+boardId;
	}

	@RequestMapping(value = "/getAllComments/{userId}/{boardId}/{snippetId}", method = RequestMethod.GET)
	public List<Comment> getAllComments(@PathVariable("userId") String userId, @PathVariable("boardId") String boardId, @PathVariable("snippetId") String snippetId, Model model) throws UnknownHostException {
		List<Comment> comments = handler.getAllComments(userId, boardId, snippetId);
		return comments;
	}
}