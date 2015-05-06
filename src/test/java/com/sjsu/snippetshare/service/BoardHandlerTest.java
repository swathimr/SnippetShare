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
    public void testGetAllBoards() throws Exception {
        org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Authorize authorize = (Authorize) context.getBean("authorizeAspect");
        BoardHandler boardHndlr = (BoardHandler) context.getBean("boardHandler");

        List<Board> accessBoards = boardHndlr.getAllBoards("5536c0f0b874c0b703a6d27e", "Shared");
        System.out.println(accessBoards.size());
        assertEquals(1, accessBoards.size());
    }
}