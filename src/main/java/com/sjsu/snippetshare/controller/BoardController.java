package com.sjsu.snippetshare.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import com.sjsu.snippetshare.aspect.Authorize;
import com.sjsu.snippetshare.domain.Board;
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
	Authorize authorize = (Authorize) context.getBean("authorizeAspect");
	BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
	
	@RequestMapping("/{userId}")
	public String getSnippeUsersHomePage(Model model,@PathVariable String userId) throws UnknownHostException
	{
		System.out.println("Mallika"+userId);
		ArrayList<Board> boardObj = boardHndlr.getAllBoards(userId, "Shared");//Board();
		System.out.println(boardObj.size());
		model.addAttribute("allBoards", boardObj);
		boardObj = boardHndlr.getAllBoards(userId, "Owned");
		System.out.println(boardObj.size());
		model.addAttribute("sharedBoards", boardObj);;
		return "SnippetUsersHome";
	}
	
	@RequestMapping(value=("/createBoard"),method=RequestMethod.POST)
	public String createBoard(Board board,RedirectAttributes redirectAttribute,User user) throws UnknownHostException
	{
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
		boardHndlr=new BoardHandler();
		
		
		if(!board.getBoardId().equals(null))
		{
			board = boardHndlr.getOneBoard(board.getBoardId());//Board();
		}
		
		model.addAttribute("board",board);
		return board;
	}
	
	@RequestMapping(value=("/updateBoard"),method=RequestMethod.POST)
	public String updateBoard(Board board,RedirectAttributes redirectAttribute,User user) throws UnknownHostException
	{
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
		boardHndlr=new BoardHandler();
		boardHndlr.deleteBoard(board.getBoardId());
		user.setId(board.getBoardOwner());
		redirectAttribute.addFlashAttribute("user", user);
		return "redirect:/SnippetUsersHome/"+user.id;
	}
	
	@RequestMapping(value=("/getBoards"),method=RequestMethod.GET)
	public String getAllBoardsByOwner(Board board,Model model) throws UnknownHostException
	{
		boardHndlr=new BoardHandler();
		System.out.println(board.getBoardOwner());
//		Map map = boardHndlr.getAllBoards(board); 
//		//map = boardHndlr.getAllBoards(board);//Board();
//		model.addAttribute("allBoards",map);
		return "SnippetUsersHome";
	}
}
