package com.sjsu.snippetshare.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import com.sjsu.snippetshare.aspect.Authorize;
import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.domain.Comment;
import com.sjsu.snippetshare.service.BoardHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sjsu.snippetshare.domain.User;

@Controller
@RequestMapping(value = ("/SnippetUsersHome"))
public class BoardController {

	org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String getSnippetUsersHomePage(Model model, @PathVariable("userId") String userId) throws UnknownHostException
	{
		Authorize authorize = (Authorize) context.getBean("authorizeAspect");
		BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
		ArrayList<Board> boardObj = boardHndlr.getAllBoards(userId, "Shared", true);//Board();
		System.out.println(boardObj.size());
		model.addAttribute("sharedBoards", boardObj);
		boardObj = boardHndlr.getAllBoards(userId, "Owned", true);
		System.out.println(boardObj.size());
		model.addAttribute("allBoards", boardObj);
		model.addAttribute("allBoards", boardObj);
		model.addAttribute("userId", userId);
		return "SnippetUsersHome";
	}

	@RequestMapping(value = "/allboards/{userId}")
	public String getAllBoards(@PathVariable("userId") String userId, User user, Model model) throws UnknownHostException {
		Authorize authorize = (Authorize) context.getBean("authorizeAspect");
		BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
		ArrayList<Board> boardObj = boardHndlr.getAllBoards(userId, "", false);
		user.setId(userId);
		model.addAttribute("allBoards", boardObj);
		model.addAttribute("user",user);
		return "GetAllBoards";
	}
	
	@RequestMapping(value=("/createBoard"),method=RequestMethod.POST)
	public String createBoard(Board board,RedirectAttributes redirectAttribute,User user) throws UnknownHostException
	{
		Authorize authorize = (Authorize) context.getBean("authorizeAspect");
		BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
		boardHndlr=new BoardHandler();
		System.out.println("got board values herre"+board.toString());
		boardHndlr.createBoard(board);
		user.setId(board.getBoardOwner());
		redirectAttribute.addFlashAttribute("user", user);
		return "redirect:/SnippetUsersHome/"+user.id;
	}
	
	@RequestMapping(value=("/getOneBoard"),method=RequestMethod.GET)
	public Board getBoard(Model model,@ModelAttribute Board board) throws UnknownHostException
	{
		Authorize authorize = (Authorize) context.getBean("authorizeAspect");
		BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
		boardHndlr=new BoardHandler();
		
		
		if(!board.getBoardId().equals(null))
		{
			board = boardHndlr.getOneBoard(board.getBoardId());//Board();
		}
		
		model.addAttribute("board", board);
		return board;
	}
	
	@RequestMapping(value=("/updateBoard"),method=RequestMethod.POST)
	public String updateBoard(Board board,RedirectAttributes redirectAttribute,User user) throws UnknownHostException
	{
		Authorize authorize = (Authorize) context.getBean("authorizeAspect");
		BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
		boardHndlr=new BoardHandler();
		System.out.println("update board value"+board.getBoardName());
		Board b = null;
		user.setId(board.getBoardOwner());
		if(board.getBoardId() != null)
		{
			b=boardHndlr.updateBoard(board);
		}
		redirectAttribute.addFlashAttribute("user", user);
		return "redirect:/SnippetUsersHome/"+user.id;
	}
	
	@RequestMapping(value=("/boardInfo/{board_id}"),method=RequestMethod.GET)
	public String boardInfo(Model model,@PathVariable String board_id) throws UnknownHostException
	{
		Authorize authorize = (Authorize) context.getBean("authorizeAspect");
		BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
		boardHndlr=new BoardHandler();
		//System.out.println(board.getBoardId());
		if(board_id!= null)
		{
			boardHndlr.getBoardInfo(board_id);
		}
		return "SnippetUsersHome";
	}
	
	
	//delets board based on board name and email id
	@RequestMapping(value=("/deleteBoard"),method=RequestMethod.POST)
	public String deleteBoard(Board board,RedirectAttributes redirectAttribute,User user) throws UnknownHostException
	{
		System.out.println("came in here"+board.getBoardId());
		Authorize authorize = (Authorize) context.getBean("authorizeAspect");
		BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
		boardHndlr.deleteBoard(board.getBoardId());
		user.setId(board.getBoardOwner());
		redirectAttribute.addFlashAttribute("user", user);
		return "redirect:/SnippetUsersHome/"+user.id;
	}
	
	@RequestMapping(value=("/getBoards"),method=RequestMethod.GET)
	public String getAllBoardsByOwner(Board board,Model model) throws UnknownHostException
	{
		Authorize authorize = (Authorize) context.getBean("authorizeAspect");
		BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
		boardHndlr=new BoardHandler();
		System.out.println(board.getBoardOwner());
//		Map map = boardHndlr.getAllBoards(board); 
//		//map = boardHndlr.getAllBoards(board);//Board();
//		model.addAttribute("allBoards",map);
		return "SnippetUsersHome";
	}
}
