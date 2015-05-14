package com.sjsu.snippetshare.controller;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.mail.*;

import com.sjsu.snippetshare.aspect.Authorize;
import com.sjsu.snippetshare.domain.*;
import com.sjsu.snippetshare.service.BoardHandler;

import org.hibernate.validator.constraints.Email;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sjsu.snippetshare.service.SnippetHandler;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Snippetcontroller {
    org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

    @RequestMapping(value = "/createSnippet/{userId}/{boardId}", method = RequestMethod.POST)
    public String createSnippet(@PathVariable ("userId") String userId, @PathVariable ("boardId") String boardId, @ModelAttribute Snippet snippet,RedirectAttributes redirectAttribute,User user,Board board) {
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        SnippetHandler snippetHandler = (SnippetHandler) context.getBean("snippetHandler");
        System.out.println("got in to create snippet"+snippet.getBoardId());
        System.out.println("got in to create snippet"+snippet.getSnippetText());
        System.out.println("got it"+snippet.getOwnerId());
        snippet.setOwnerId(userId);
        snippet.setBoardId(boardId);
        Snippet dbSnippet = snippetHandler.addSnippet(boardId,snippet);
        if (dbSnippet == null) {
            return "Failed!";
        }
        System.out.println("created one is::::::::::::::" + dbSnippet);
        board.setBoardId(snippet.getBoardId());
        redirectAttribute.addFlashAttribute("board", board);
        redirectAttribute.addFlashAttribute("user", user);
        return "redirect:/getAllSnippets/"+userId+"/"+boardId;
    }

    @RequestMapping(value = "/getSnippet/{boardId}/{snippetId}", method = RequestMethod.GET)
    public String createSnippet(@PathVariable String boardId, @PathVariable String snippetId, Model model) {
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        SnippetHandler snippetHandler = (SnippetHandler) context.getBean("snippetHandler");
        Snippet dbSnippet = snippetHandler.getSnippet(boardId, snippetId);
        if (dbSnippet == null) {
            return "Failed!";
        }
        return "Success!";
    }

    @RequestMapping(value = "/updateSnippet/{boardId}", method = RequestMethod.POST)
    public String updateSnippet(@PathVariable String boardId, @ModelAttribute Snippet snippet, Model model) {
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        SnippetHandler snippetHandler = (SnippetHandler) context.getBean("snippetHandler");
        Snippet dbSnippet = snippetHandler.updateSnippet(boardId, snippet);
        if (dbSnippet == null) {
            return "Failed!";
        }
        return "Success!";
    }

    @RequestMapping(value = "/getAllSnippets/{userId}/{boardId}")
    public String getSnippet(Model model,@PathVariable String userId, @PathVariable String boardId) {
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        SnippetHandler snippetHandler = (SnippetHandler) context.getBean("snippetHandler");
        BoardHandler boardHandler = (BoardHandler) context.getBean("boardHandler");
        ArrayList<ArrayList<Snippet>> masterList = snippetHandler.getAllSnippets(boardId, userId);
        ArrayList<Snippet> editableSnippets = masterList.get(0);
        ArrayList<Snippet> noneditableSnippets = masterList.get(1);
        Board parentBoard = new Board();
        try {
            parentBoard = boardHandler.getOneBoard(boardId);
        } catch (UnknownHostException uhe) {
            System.out.println("Unknown Host exception for Get Board");
        }
        System.out.println("board id from get all snippets"+boardId);
        model.addAttribute("boardId", boardId);
        model.addAttribute("userId", userId);
        model.addAttribute("editableSnippets",editableSnippets);
        model.addAttribute("noneditableSnippets",noneditableSnippets);
        model.addAttribute("parentBoard", parentBoard);
        model.addAttribute("comment",new Comment());
        return "ViewSnippets";
    }

    @RequestMapping(value ="/deleteSnippet/{boardId}/{snippetId}/{userId}",method = RequestMethod.POST)
    public String deleteSnippet(@PathVariable String boardId, @PathVariable String snippetId,@PathVariable String userId) {
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        SnippetHandler snippetHandler = (SnippetHandler) context.getBean("snippetHandler");
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
                                   Model model)
    {
        boolean userExists = false;
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        SnippetHandler hand = (SnippetHandler) context.getBean("snippetHandler");
        System.out.println("addUsersToBoard : board id "+ boardId);
        try {
            ArrayList<String> userList = new ArrayList<String>();
            if(!emailId1.equals("") && !emailId1.equals(null))
            {
                System.out.println("emailId1 is : " + emailId1);
                //userList.add(emailId1);
                if(hand.updateAccessList(boardId, emailId1))
                {
                    userExists = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/getAllSnippets/"+userId+"/"+boardId;
    }


    @RequestMapping(value = "/sendEmail",method = RequestMethod.POST)
    public String getSnippet(com.sjsu.snippetshare.domain.Email email) throws UnsupportedEncodingException{
        String userId=email.getUserId();
        String boardId=email.getBoardId();
        System.out.println("Got snippet hereeeeeeeee::" + email.getSnippetText() + "and email id is::"+email.getEmailId());
        SnippetHandler snippethndlr = new SnippetHandler();
        snippethndlr.sendEmail(email.getSnippetText(),email.getEmailId());
        return "redirect:/getAllSnippets/"+userId+"/"+boardId;
    }

}
