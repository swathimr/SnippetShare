package com.sjsu.snippetshare.controller;

import java.net.UnknownHostException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.sjsu.snippetshare.domain.User;
import com.sjsu.snippetshare.service.MongoFactory;
import com.sjsu.snippetshare.service.UserHandler;

@Controller
@RequestMapping(value = ("/snippetshare/users"))
public class UserController {
	
	DBCollection coll; 
	BasicDBObject doc;
	UserHandler userHndlr;

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String loginUser(@ModelAttribute User user,Model model) throws UnknownHostException
	{
		System.out.println("yup in here"+user.getEmail());
		userHndlr= new UserHandler();
		boolean userVal=userHndlr.checkIfUserExists(user);
		if(userVal)
		{
		return "redirect:/SnippetUsersHome";
		}
		else
		{
		return "redirect:/snippetshare";
		}
	}
	
	
	// facebook login
	@RequestMapping(value="/fblogin",method=RequestMethod.POST)
	public String loginFbUser(User user,RedirectAttributes redirectAttribute) throws UnknownHostException
	{
		System.out.println("yup in here"+user.getEmail()+"FB name is:::"+user.getName()+user.getPassword());
		userHndlr= new UserHandler();
		boolean userVal=userHndlr.checkIfUserExists(user);
		if(userVal)
		{
			//model.addAttribute("user",user);
			redirectAttribute.addFlashAttribute("user",user);
			return "redirect:/SnippetUsersHome";
		}
		else
		{
			userHndlr.SignUpUser(user);
			return "redirect:/SnippetUsersHome";
		}
	}
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public String createUser(@ModelAttribute User user,Model model) throws UnknownHostException
	{
		userHndlr= new UserHandler();
		boolean userVal=userHndlr.checkIfUserExists(user);
		if(!userVal)
		{
			System.out.println("Email id does not exist.So signing up the user");
			userHndlr.SignUpUser(user);
		}
		return "redirect:/SnippetUsersHome";
	}

}
