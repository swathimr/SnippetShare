package com.sjsu.snippetshare.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
public class HomeController 
{	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(RestConfig.class, args);
    }
}
