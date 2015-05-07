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
        String boardId = "55491bd2b8747d2e080befae";
        String snippetId = "1";
        Comment comment = new Comment("5536c0f0b874c0b703a6d27e", "Test Comment 1");
        //commentsHandler.createComment(comment, "5536c0f0b874c0b703a6d27e", boardId, snippetId);
    }

    @Test
    public void testGetComment() throws Exception {
        CommentsHandler commentsHandler = new CommentsHandler();
        String boardId = "55491bd2b8747d2e080befae";
        String snippetId = "1";
        String commentId = "5549ca5f77c8621d2842be9d";
        commentsHandler.deleteComment(boardId, snippetId, commentId);
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