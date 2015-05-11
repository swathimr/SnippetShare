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
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value ="/addUsersToBoard/{boardId}/{userId}",method = RequestMethod.POST)
    public String updateAccessList(@PathVariable(value="boardId") String boardId,
                                              @PathVariable(value="userId") String userId,
                                              @RequestParam(value="emailId1") String emailId1,
                                              @RequestParam(value="emailId2") String emailId2,
                                              @RequestParam(value="emailId3") String emailId3,
                                              @RequestParam(value="emailId4") String emailId4,
                                              @RequestParam(value="emailId5") String emailId5,
                                              Model model)
    {
        System.out.println("addUsersToBoard : board id "+ boardId);
        SnippetHandler hand = new SnippetHandler();
        try {
            ArrayList<String> userList = new ArrayList<String>();
            if(!emailId1.equals(null))
            {
                System.out.println("emailId1 is : "+ emailId1);
                userList.add(emailId1);
            }
            if(!emailId2.equals(null))
            {
                userList.add(emailId2);
            }
            if(!emailId3.equals(null))
            {
                userList.add(emailId3);
            }
            if(!emailId4.equals(null))
            {
                userList.add(emailId4);
            }
            if(!emailId5.equals(null))
            {
                userList.add(emailId5);
            }
            for(int i=0; i<userList.size();i++)
            System.out.println(userList.get(i));
            //hand.updateAccessList(boardId, userList);
            hand.updateAccessList(boardId, userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/getAllSnippets/"+userId+"/"+boardId;
    }
}
