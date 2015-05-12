package com.sjsu.snippetshare.aspect;

import com.sjsu.snippetshare.domain.Board;
import com.sjsu.snippetshare.domain.Comment;
import com.sjsu.snippetshare.domain.Snippet;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mallika on 5/5/15.
 */

@Aspect
public class Authorize {

    @Around("execution(public* com.sjsu.snippetshare.service.BoardHandler.getAllBoards(..))")
    public Object doBoardAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        String userId = (String) args[0];
        String condition = (String) args[1];
        boolean accessPrivacy = (Boolean) args[2];
        System.out.println("Before");
        System.out.println(accessPrivacy);
        System.out.println("In aspect");
        List<Board> boards = (List<Board>) pjp.proceed();
        if (accessPrivacy) {
            List<Board> filteredBoards = new ArrayList<Board>();
            if (condition.equals("Owned")) {
                for (Board board : boards) {
                    if (board.getBoardOwner().equals(userId)) {
                        filteredBoards.add(board);
                    }
                }
            } else if (condition.equals("Shared")) {
                for (Board board : boards) {
                    List<String> accessList = board.getAccessList();
                    if (accessList != null) {
                        for (String accessPerson : accessList) {
                            if (userId.equals(accessPerson)) {
                                filteredBoards.add(board);
                                break;
                            }
                        }
                    }
                }
            }
            return filteredBoards;
        }
        return boards;
    }

    @Around("execution(public* com.sjsu.snippetshare.service.SnippetHandler.getAllSnippets(..))")
    public Object doSnippetAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        String boardId = (String) args[0];
        String userId = (String) args[1];
        ArrayList<ArrayList<Snippet>> master = (ArrayList<ArrayList<Snippet>>) pjp.proceed();
        ArrayList<Snippet> snippets = master.get(0);
        ArrayList<Snippet> editableSnippetList = new ArrayList<Snippet>();
        ArrayList<Snippet> nonEditableSnippetList = new ArrayList<Snippet>();
        for (Snippet snippet : snippets) {
            if (userId.equals(snippet.getOwnerId())) {
                editableSnippetList.add(snippet);
            } else {
                nonEditableSnippetList.add(snippet);
            }
        }
        ArrayList<ArrayList<Snippet>> masterSnippetList = new ArrayList<ArrayList<Snippet>>();
        masterSnippetList.add(0, editableSnippetList);
        masterSnippetList.add(1, nonEditableSnippetList);
        return masterSnippetList;
    }

    @Around("execution(public* com.sjsu.snippetshare.service.CommentsHandler.getAllComments(..))")
    public Object doCommentAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        String userId = (String) args[0];
        String boardId = (String) args[1];
        String snippetId = (String) args[2];
        List<Comment> comments = (List<Comment>) pjp.proceed();
        List<Comment> editableCommentList = new ArrayList<Comment>();
        List<Comment> nonEditableCommentList = new ArrayList<Comment>();
        for (Comment comment : comments) {
            if (userId.equals(comment.getOwnerId())) {
                editableCommentList.add(comment);
            } else {
                nonEditableCommentList.add(comment);
            }
        }
        List<List<Comment>> masterCommentList = new ArrayList<List<Comment>>();
        masterCommentList.add(editableCommentList);
        masterCommentList.add(nonEditableCommentList);
        return masterCommentList;
    }
}
