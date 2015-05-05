package com.sjsu.snippetshare.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import com.sjsu.snippetshare.domain.Board;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.domain.User;
import com.sjsu.snippetshare.service.BoardHandler;

@ComponentScan
@RestController
@RequestMapping(value = ("/SnippetUsersHome"))
public class BoardController {
	
	BoardHandler boardHndlr;
	
	@RequestMapping("/{userId}")
	public String getSnippeUsersHomePage(Model model,@PathVariable String userId) throws UnknownHostException
	{
		boardHndlr=new BoardHandler();
		ArrayList<Map> newList = new ArrayList<Map>();
		System.out.println("5536c0f0b874c0b703a6d27e");
		ArrayList<Board> boardObj = boardHndlr.getAllBoards(userId, "Shared");//Board();
		for(Board b : boardObj)
		{
			System.out.println("Get board details");
			System.out.println(b.getBoardId());
			System.out.println(b.getBoardName());
		}
		
		model.addAttribute("ownerBoards",boardObj);
		
		
		// Mallika pls add aop for here nd fetch me the boards only i want to share
		model.addAttribute("sharedBoards",boardObj);
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
	
	
	@RequestMapping(value=("/updateBoard"),method=RequestMethod.PUT)
	public Board updateBoard(Model model,@ModelAttribute Board board) throws UnknownHostException
	{
		boardHndlr=new BoardHandler();
		Board b = null;
		System.out.println("Board::"+board.getBoardId());
		if(board.getBoardId() != null)
		{
			b=boardHndlr.updateBoard(board);
		}
		//return "SnippetUsersHome";
		return b;
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
	@RequestMapping(value=("/deleteBoard"),method=RequestMethod.DELETE)
	public String deleteBoard(Board board,RedirectAttributes redirectAttribute,User user) throws UnknownHostException
	{
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
