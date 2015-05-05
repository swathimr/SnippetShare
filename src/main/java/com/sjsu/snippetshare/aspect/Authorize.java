package com.sjsu.snippetshare.aspect;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by mallika on 5/5/15.
 */

@Aspect
public class Authorize {

    @Around("execution(public* service.BoardHandler.)")
}
