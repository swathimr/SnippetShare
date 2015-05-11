package com.sjsu.snippetshare.controller;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.snippetshare.aspect.Authorize;
import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.domain.Comment;
import com.sjsu.snippetshare.domain.User;
import com.sjsu.snippetshare.service.BoardHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sjsu.snippetshare.domain.Snippet;
import com.sjsu.snippetshare.service.SnippetHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Snippetcontroller {
    org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
    Authorize authorize = (Authorize) context.getBean("authorizeAspect");
    SnippetHandler snippetHandler = (SnippetHandler) context.getBean("snippetHandler");
/*
    @RequestMapping(value = "/createSnippet/{boardId}", method = RequestMethod.POST)
    public String createSnippet(@PathVariable String boardId, @ModelAttribute Snippet snippet, Model model) {
        Snippet dbSnippet = snippetHandler.addSnippet(boardId, snippet);
        if (dbSnippet == null) {
            return "Failed!";
        }
        return "Success!";
    }
    */

    @RequestMapping(value = "/createSnippet", method = RequestMethod.POST)
    public String createSnippet(@ModelAttribute Snippet snippet,RedirectAttributes redirectAttribute,User user,Board board) {
        System.out.println("got in to create snippet"+snippet.getBoardId());
        System.out.println("got in to create snippet"+snippet.getSnippetText());
        System.out.println("got it"+snippet.getOwnerId());
        Snippet dbSnippet = snippetHandler.addSnippet(snippet.getBoardId(),snippet);
        if (dbSnippet == null) {
            return "Failed!";
        }
        System.out.println("created one is::::::::::::::"+dbSnippet);
        board.setBoardId(snippet.getBoardId());
        redirectAttribute.addFlashAttribute("board",board);
        redirectAttribute.addFlashAttribute("user", user);
        return "redirect:/getAllSnippets/"+user.id+"/"+board.getBoardId();
    }

    @RequestMapping(value = "/getSnippet/{boardId}/{snippetId}", method = RequestMethod.GET)
    public String createSnippet(@PathVariable String boardId, @PathVariable String snippetId, Model model) {
        Snippet dbSnippet = snippetHandler.getSnippet(boardId, snippetId);
        if (dbSnippet == null) {
            return "Failed!";
        }
        return "Success!";
    }

    @RequestMapping(value = "/updateSnippet/{boardId}", method = RequestMethod.POST)
    public String updateSnippet(@PathVariable String boardId, @ModelAttribute Snippet snippet, Model model) {
        Snippet dbSnippet = snippetHandler.updateSnippet(boardId, snippet);
        if (dbSnippet == null) {
            return "Failed!";
        }
        return "Success!";
    }

    @RequestMapping(value = "/getAllSnippets/{userId}/{boardId}")
    public String getSnippet(Model model,@PathVariable String userId, @PathVariable String boardId) {
        ArrayList<Snippet> snippets = snippetHandler.getAllSnippets(boardId);
        System.out.println("board id from get all snippets"+boardId);
        model.addAttribute("boardId", boardId);
        model.addAttribute("userId", userId);
        model.addAttribute("snippets",snippets);
        model.addAttribute("comment",new Comment());
        return "ViewSnippets";
    }

    @RequestMapping(value ="/deleteSnippet/{boardId}/{snippetId}/{userId}",method = RequestMethod.POST)
    public String deleteSnippet(@PathVariable String boardId, @PathVariable String snippetId,@PathVariable String userId) {
        System.out.println("in delete snippet api" + boardId + snippetId + userId);
        boolean result = snippetHandler.deleteSnippet(boardId, snippetId);
        if (!result)
        {
            return "Failed!";
        }
        return "redirect:/getAllSnippets/"+userId+"/"+boardId;
    }

    @RequestMapping(value ="/addUsersToBoard/{boardId}/{userId}/{u1}/{u2}/{u3}",method = RequestMethod.POST)
    public String updateAccessList(@PathVariable String boardId,
                                              @PathVariable String userId,
                                              //@ModelAttribute ArrayList<String> userList,
                                   @PathVariable String u1,
                                   @PathVariable String u2,
                                   @PathVariable String u3,
                                              Model model)
    {

        SnippetHandler hand = new SnippetHandler();
        try {
            ArrayList<String> userList = new ArrayList<String>();
            if(!u1.equals(null))
            {
                userList.add(u1);
            }
            if(!u2.equals(null))
            {
                userList.add(u2);
            }
            if(!u3.equals(null))
            {
                userList.add(u3);
            }

            //hand.updateAccessList(boardId, userList);
            hand.updateAccessList(boardId, userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/getAllSnippets/"+userId+"/"+boardId;

    }




}
