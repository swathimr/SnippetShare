package com.sjsu.snippetshare.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.domain.Comment;
import com.sjsu.snippetshare.domain.Snippet;
import com.sjsu.snippetshare.domain.User;
import com.sjsu.snippetshare.service.MongoFactory;
import com.sjsu.snippetshare.service.SearchHandler;

@Controller
public class SearchController {
	DBObject a = new BasicDBObject();
	Snippet s;
	Comment c;
	ArrayList<Comment> commentList = new ArrayList<Comment>();
	List<Snippet> snippetList;
	List<User> userList;
	
	@RequestMapping(value = "/searchPage/{userId}", method = RequestMethod.GET)
	public String searchPage(@PathVariable("userId") String userId, Model model) {
		model.addAttribute("userId",userId);
		return "SearchPage";
	}
		

	@RequestMapping(value = "/searchSnippets/{userId}", method = RequestMethod.GET)
	public String searchSnippets(Model model, @PathVariable ("userId") String userId, @RequestParam(value = "snippet") String search) {
		
		SearchHandler searchhandler = new SearchHandler();
		ArrayList<Snippet> snippetList = searchhandler.searchSnippets(search);
		if (snippetList == null) {
			snippetList = new ArrayList<Snippet>();
		} 
		model.addAttribute("snippetList", snippetList);
		for (int i = 0; i < snippetList.size(); i++) {
			System.out.println(snippetList.get(i).toString());
		}
		model.addAttribute("userId", userId);
		return "SearchSnippetsPage";
	}

	@RequestMapping(value = "/searchBoards/{userId}", method = RequestMethod.GET)
	public String searchBoards(Model model,
			@RequestParam(value = "board") String search, @PathVariable("userId") String userId) throws Exception {
		SearchHandler searchhandler = new SearchHandler();
		ArrayList<Board> boardList = searchhandler.searchBoards(search);
		if (boardList == null) {
			boardList = new ArrayList<Board>();
		} 
		System.out.println("Board List" + boardList.get(0).getBoardName());
		model.addAttribute("boardList", boardList);
		model.addAttribute("userId", userId);
		return "SearchBoardsPage";
	}

	@RequestMapping(value = "/searchUsers/{userId}", method = RequestMethod.GET)
	public String searchUsers(Model model, @PathVariable ("userId") String userId,
			@RequestParam(value = "user") String search) throws Exception {
		SearchHandler searchhandler = new SearchHandler();
		ArrayList<User> userList = searchhandler.searchUsers(search);
		if (userList == null) {
			userList = new ArrayList<User>();
		}
		System.out.println("User List");
		model.addAttribute("userList", userList);
		model.addAttribute("userId", userId);
		return "SearchUsersPage";
	}
}
