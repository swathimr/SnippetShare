package com.sjsu.snippetshare.service;

import com.sjsu.snippetshare.controller.HomeController;
import com.sjsu.snippetshare.domain.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
        BoardHandler boardHandler = new BoardHandler();
        List<Board> accessBoards = boardHandler.getAllBoards("sindhu", "Shared");
        assertEquals(5, accessBoards.size());
        accessBoards = boardHandler.getAllBoards("swathi6489@gmail.com", "Owned");
        assertEquals(4, accessBoards.size());
    }
}