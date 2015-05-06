package com.sjsu.snippetshare.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class HomeController 
{	
	public static void main(String[] args) throws Exception {
        org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        SpringApplication.run(HomeController.class);
    }
}
