package com.sjsu.snippetshare.controller;

import org.springframework.boot.SpringApplication;
<<<<<<< Updated upstream
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.context.annotation.ComponentScan;
>>>>>>> Stashed changes

/**
 * Hello world!
 *
 */
@ComponentScan
public class HomeController 
{	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(RestConfig.class, args);
    }
}
