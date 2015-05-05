package com.sjsu.snippetshare.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

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
		Map map = boardHndlr.getAllBoards("5536c0f0b874c0b703a6d27e");//Board();
		System.out.println("map valyue got is :::"+map);
		newList.add(map);
		model.addAttribute("allBoards",newList);
		
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
	
	@RequestMapping(value=("/getBoard"),method=RequestMethod.GET)
	public String getBoard(Board board) throws UnknownHostException
	{
		boardHndlr=new BoardHandler();
		//boardHndlr.getAllBoards();//Board();
		return "SnippetUsersHome";
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
}
