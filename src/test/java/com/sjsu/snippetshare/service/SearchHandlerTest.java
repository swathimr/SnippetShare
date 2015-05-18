package com.sjsu.snippetshare.service;

import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.domain.Snippet;
import com.sjsu.snippetshare.domain.User;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by mallika on 5/18/15.
 */
public class SearchHandlerTest {

    @Test
    public void testSearchSnippets() throws Exception {
        SearchHandler searchHandler = new SearchHandler();
        ArrayList<Snippet> snippets = searchHandler.searchSnippets("http");
        //assertEquals(snippets.size(), 5);
    }

    @Test
    public void testSearchBoards() throws Exception {
        SearchHandler searchHandler = new SearchHandler();
        ArrayList<Board> boards = searchHandler.searchBoards("Mongo");
        //assertEquals(boards.size(), 2);
    }

    @Test
    public void testSearchUsers() throws Exception {
        SearchHandler searchHandler = new SearchHandler();
        ArrayList<User> users = searchHandler.searchUsers("mallika");
        //assertEquals(users.size(), 1);
    }
}