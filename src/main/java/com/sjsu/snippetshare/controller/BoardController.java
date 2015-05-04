package com.sjsu.snippetshare.controller;

import java.net.UnknownHostException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.service.BoardHandler;

@Controller
@RequestMapping(value = ("/SnippetUsersHome"))
public class BoardController {
	
	BoardHandler boardHndlr;
	
	@RequestMapping
	public String getSnippeUsersHomePage()
	{
		return "SnippetUsersHome";
	}
	
	@RequestMapping(value=("/createBoard"),method=RequestMethod.POST)
	public String createBoard(Board board) throws UnknownHostException
	{
		boardHndlr=new BoardHandler();
		boardHndlr.createBoard(board);
		return "SnippetUsersHome";
	}
	
	@RequestMapping(value=("/getBoard"),method=RequestMethod.GET)
	public String getBoard(Board board) throws UnknownHostException
	{
		boardHndlr=new BoardHandler();
		boardHndlr.getAllBoards();//Board();
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
