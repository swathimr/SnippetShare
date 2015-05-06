package com.sjsu.snippetshare.service;

import com.sjsu.snippetshare.domain.Comment;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mallika on 5/4/15.
 */

public class CommentsHandlerTest {

    @Test
    public void testAddComment() throws Exception {
        CommentsHandler commentsHandler = new CommentsHandler();
        String boardId = "553dbaf6b874231faaaeed27";
        String snippetId = "55486125b8748c0c1755ac47";
        Comment comment = new Comment("5536c0f0b874c0b703a6d27e", "Test Comment 1");
        //commentsHandler.createComment(boardId, snippetId, comment);
    }

    @Test
    public void testGetComment() throws Exception {

    }

    @Test
    public void testUpdateComment() throws Exception {

    }

    @Test
    public void testDeleteComment() throws Exception {

    }

    @Test
    public void testGetAllComments() throws Exception {

    }
}