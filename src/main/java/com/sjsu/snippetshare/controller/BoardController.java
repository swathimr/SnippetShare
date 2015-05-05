package com.sjsu.snippetshare.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.domain.User;
import com.sjsu.snippetshare.service.BoardHandler;

@Controller
@RequestMapping(value = ("/SnippetUsersHome"))
public class BoardController {
	
	BoardHandler boardHndlr;
	
	@RequestMapping("/{userId}")
	public String getSnippeUsersHomePage(Model model,@PathVariable String userId) throws UnknownHostException
	{
		boardHndlr=new BoardHandler();
		ArrayList<Map> newList = new ArrayList<Map>();
		System.out.println("5536c0f0b874c0b703a6d27e");
		ArrayList<Board> boardObj = boardHndlr.getAllBoards(userId);//Board();
		//System.out.println("map valyue got is :::"+array);
		for(Board b : boardObj)
		{
			System.out.println("Get board details");
			System.out.println(b.getBoardId());
			System.out.println(b.getBoardName());
		}
		
		model.addAttribute("allBoards",boardObj);
		
		return "SnippetUsersHome";
	}
	
	@RequestMapping(value=("/createBoard"),method=RequestMethod.POST)
	public String createBoard(Board board,Model model,User user) throws UnknownHostException
	{
		boardHndlr=new BoardHandler();
		System.out.println("got board values herre"+board.toString());
		boardHndlr.createBoard(board);
		user.setId(board.getBoardOwner());
		model.addAttribute("user",user);
		return "SnippetUsersHome";
	}
	
	@RequestMapping(value=("/getOneBoard/{boardID}"),method=RequestMethod.GET)
	public Board getBoard(Model model,@PathVariable String boardID) throws UnknownHostException
	{
		boardHndlr=new BoardHandler();
		Board board = null;
		
		if(!boardID.equals(null))
		{
			board = boardHndlr.getOneBoard(boardID);//Board();
		}
		
		model.addAttribute("board",board);
		//return "SnippetUsersHome";
		return board;
	}
	
	@RequestMapping(value=("/updateBoard"),method=RequestMethod.PUT)
	public String updateBoard(Board board) throws UnknownHostException
	{
		boardHndlr=new BoardHandler();
		boardHndlr.updateBoard(board);
		return "SnippetUsersHome";
	}
	
	//delets board based on board name and email id
	@RequestMapping(value=("/deleteBoard/{boardname}"),method=RequestMethod.DELETE)
	public void deleteBoard(@PathVariable("boardname") String boardname) throws UnknownHostException
	{
		boardHndlr=new BoardHandler();
		boardHndlr.deleteBoard(boardname);
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
