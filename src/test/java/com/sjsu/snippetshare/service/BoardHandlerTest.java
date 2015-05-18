package com.sjsu.snippetshare.service;

import com.sjsu.snippetshare.aspect.Authorize;
import com.sjsu.snippetshare.controller.HomeController;
import com.sjsu.snippetshare.domain.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mallika on 5/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HomeController.class)
public class BoardHandlerTest {

    @Test
    public void testGetAllBoardsPrivate () throws Exception {
        org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");

//        List<Board> accessBoards = boardHndlr.getAllBoards("5536c0f0b874c0b703a6d27e", true);
//        System.out.println(accessBoards.size());
//        assertEquals(1, accessBoards.size());
    }

    @Test
    public void testGetAllBoardsPublic () throws Exception {
        org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");

//        List<Board> accessBoards = boardHndlr.getAllBoards("5536c0f0b874c0b703a6d27e", false);
//        System.out.println(accessBoards.size());
//        assertEquals(1, accessBoards.size());
    }

    @Test
    public void createBoard () throws Exception {
        org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
        Board board = new Board();
        board.setBoardName("JUnit Test Board");
        board.setBoardOwner("5536c0f0b874c0b703a6d27e");
        board.setCategory("Java");
        board.setPrivacy("Private");
        boardHndlr.createBoard(board);
        //assertEquals(boardHndlr.getAllBoards("5536c0f0b874c0b703a6d27e", true).size(), 1);
    }

    @Test
    public void updateBoard () throws Exception {
        org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
        Board board = new Board();
        board.setBoardName("JUnit Test Board Updated");
        board.setBoardId("553dbaf6b874231faaaddd56");
        board.setBoardOwner("5536c0f0b874c0b703a6d27e");
        board.setCategory("Java");
        board.setPrivacy("Private");
        boardHndlr.updateBoard(board);
        //assertEquals(boardHndlr.getAllBoards("5536c0f0b874c0b703a6d27e", true).size(), 1);
    }

    @Test
    public void deleteBoard() throws Exception {
        org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");
        boardHndlr.deleteBoard("553dbaf6b874231faaaddd56");
        //assertEquals(boardHndlr.getAllBoards("5536c0f0b874c0b703a6d27e", true).size(), 0);
    }
}