package com.sjsu.snippetshare.controller;

import java.net.UnknownHostException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.sjsu.snippetshare.domain.User;
import com.sjsu.snippetshare.service.UserHandler;

@Controller
@RequestMapping(value = ("/snippetshare/users"))
public class UserController {

	DBCollection coll;
	BasicDBObject doc;
	UserHandler userHndlr = new UserHandler();

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String loginUser(@ModelAttribute User user,RedirectAttributes redirectAttribute) throws UnknownHostException
	{
		System.out.println("yup in here"+user.getEmail());

		boolean userVal=userHndlr.checkIfUserExists(user);
		if(userVal)
		{
			redirectAttribute.addFlashAttribute("user",user);
			return "redirect:/SnippetUsersHome/"+user.id;
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
		boolean userVal=userHndlr.checkIfUserExists(user);
		if(userVal)
		{
			//model.addAttribute("user",user);
			System.out.println(user.getId());
			redirectAttribute.addFlashAttribute("user",user);
			return "redirect:/SnippetUsersHome/"+user.id;
		}
		else
		{
			userHndlr.SignUpUser(user);
			redirectAttribute.addFlashAttribute("user",user);
			return "redirect:/SnippetUsersHome/"+user.id;
		}
	}

	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public String createUser(@ModelAttribute User user,RedirectAttributes redirectAttribute) throws UnknownHostException
	{
		boolean userVal=userHndlr.checkIfUserExists(user);
		if(!userVal)
		{
			System.out.println("Email id does not exist.So signing up the user");
			userHndlr.SignUpUser(user);
		}
		redirectAttribute.addFlashAttribute("user",user);
		return "redirect:/SnippetUsersHome/"+user.id;
	}

	@RequestMapping(value = "/settings/{userId}", method=RequestMethod.GET)
	public String settings(@PathVariable ("userId") String userId, Model model) throws UnknownHostException {
		User user = userHndlr.getUser(userId);
		model.addAttribute("user", user);
		model.addAttribute("updateUser", new User());
		return "settings";
	}

	@RequestMapping(value = "/update/{userId}", method = RequestMethod.POST)
	public String updateUser(@PathVariable ("userId") String userId, @ModelAttribute User updateUser, Model model) throws UnknownHostException {
		User user = userHndlr.updateUser(userId, updateUser);
		model.addAttribute("user", user);
		model.addAttribute("updateUser", new User());
		return "settings";
	}

}
