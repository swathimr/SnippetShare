package com.sjsu.snippetshare.controller;

import com.sjsu.snippetshare.domain.Snippet;
import com.sjsu.snippetshare.service.SnippetHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@EnableAutoConfiguration
@org.springframework.web.bind.annotation.RestController

public class Snippetcontroller {
    SnippetHandler snippetHandler = new SnippetHandler();

    @RequestMapping(value = "/createSnippet/{boardId}", method = RequestMethod.POST)
    public String createSnippet(@PathVariable String boardId, @ModelAttribute Snippet snippet, Model model) {
        Snippet dbSnippet = snippetHandler.addSnippet(boardId, snippet);
        if (dbSnippet == null) {
            return "Failed!";
        }
        return "Success!";
    }

    @RequestMapping(value = "/getSnippet/{boardId}/{snippetId}", method = RequestMethod.GET)
    public String createSnippet(@PathVariable String boardId, @PathVariable String snippetId, Model model) {
        Snippet dbSnippet = snippetHandler.getSnippet(boardId, snippetId);
        if (dbSnippet == null) {
            return "Failed!";
        }
        return "Success!";
    }

    @RequestMapping(value = "/updateSnippet/{boardId}", method = RequestMethod.POST)
    public String updateSnippet(@PathVariable String boardId, @ModelAttribute Snippet snippet, Model model) {
        Snippet dbSnippet = snippetHandler.updateSnippet(boardId, snippet);
        if (dbSnippet == null) {
            return "Failed!";
        }
        return "Success!";
    }

    @RequestMapping(value = "/getSnippet/{boardId}/{snippetId}", method = RequestMethod.POST)
    public String deleteSnippet(@PathVariable String boardId, @PathVariable String snippetId, Model model) {
        boolean result = snippetHandler.deleteSnippet(boardId, snippetId);
        if (!result) {
            return "Failed!";
        }
        return "Success!";
    }

    @RequestMapping(value = "/getAllSnippets/{boardId}", method = RequestMethod.POST)
    public List<Snippet> deleteSnippet(@PathVariable String boardId, Model model) {
        List<Snippet> snippets = snippetHandler.getAllSnippets(boardId);
        return snippets;
    }

}
