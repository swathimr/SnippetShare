package com.sjsu.snippetshare.service;

import com.sjsu.snippetshare.controller.HomeController;
import com.sjsu.snippetshare.domain.Snippet;
import com.sjsu.snippetshare.service.SnippetHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;
import java.util.List;

/*
 * Created by mallika on 5/4/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HomeController.class)
public class SnippetHandlerTest {
    SnippetHandler snippetHandler = new SnippetHandler();

    @Test
    public void testAddSnippet() throws Exception {
        String boardId = "553dbaf6b874231faaaeed27";
        Snippet snippet = new Snippet("TestSnippetMallika3", "5536c0f0b874c0b703a6d27e", "Mallika's Test Snippet");
        assertNotNull(snippetHandler.addSnippet(boardId, snippet));
    }

    @Test
    public void testGetSnippet() throws Exception {
        String boardId = "553dbaf6b874231faaaeed27";
        String snippetId = "55481bd377c8c813a532f7a3";
        Snippet snippet = snippetHandler.getSnippet(boardId, snippetId);
        assertNotNull(snippet);
        assertEquals(snippet.getSnippetName(), "TestSnippetMallika");
    }

    @Test
    public void testUpdateSnippet() throws Exception {
        String boardId = "553dbaf6b874231faaaeed27";
        Snippet snippet = new Snippet("UpdatedSnippetMallika", "5536c0f0b874c0b703a6d27e", "Mallika's Test Snippet");
        snippet.setSnippetId("55481bd377c8c813a532f7a3");
        Snippet newSnippet = snippetHandler.updateSnippet(boardId, snippet);
        assertNotNull(snippet);
        assertEquals(snippet.getSnippetName(), "UpdatedSnippetMallika");
    }

    @Test
    public void testDeleteSnippet() throws Exception {
        String boardId = "553dbaf6b874231faaaeed27";
        String snippetId = "55481bd377c8c813a532f7a3";
        boolean result = snippetHandler.deleteSnippet(boardId, snippetId);
        assertEquals(result, true);
    }

    @Test
    public void testGetAllSnippets() throws Exception {
        String boardId = "553dbaf6b874231faaaeed27";
        List<Snippet> snippets = snippetHandler.getAllSnippets(boardId);
        assertEquals(snippets.size(), 4);
    }
}