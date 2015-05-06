package com.sjsu.snippetshare.aspect;

import com.sjsu.snippetshare.domain.Board;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mallika on 5/5/15.
 */

@Aspect
public class Authorize {

    @Around("execution(public* com.sjsu.snippetshare.service.BoardHandler.getAllBoards(..))")
    public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        String userId = (String) args[0];
        String condition = (String) args[1];
        System.out.println("Before");
        Object retVal = pjp.proceed();
        System.out.println("In aspect");
      List<Board> boards = (List<Board>) pjp.proceed();
        List <Board> filteredBoards = new ArrayList<Board>();
        if (condition.equals("Owned")) {
            for (Board board : boards) {
                if (board.getBoardOwner().equals(userId)) {
                    filteredBoards.add(board);
                }
            }
        } else if (condition.equals("Shared")) {
            for (Board board : boards) {
                if (board.getAccessList() != null) {
                    if (userId.equals(board.getAccessList().get(0))) {
                        filteredBoards.add(board);
                    }
                }
            }
        }
        return filteredBoards;
    }
}
