package com.xetius.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class HttpHandlerFactoryTest {

    private HttpHandlerFactory factory;
    private HttpExchange httpExchange;

    @Before
    public void setUp() {
        factory = new HttpHandlerFactory();
        httpExchange = mock(HttpExchange.class);
    }

    @Test
    public void testLoginURIReturnsLoginRequestHandler() {
        given(httpExchange.getRequestURI()).willReturn(URI.create("/4711/login"));
        given(httpExchange.getRequestMethod()).willReturn("GET");

        HttpHandler httpHandler = factory.getHandler(httpExchange);
        Assert.assertTrue(httpHandler instanceof LoginRequestHandler);
    }

    @Test
    public void testScoreURIReturnsScoreUpdateRequestHandler() {
        given(httpExchange.getRequestURI()).willReturn(URI.create("/2/score?sessionkey=UICSNDK"));
        given(httpExchange.getRequestMethod()).willReturn("POST");

        HttpHandler httpHandler = factory.getHandler(httpExchange);
        Assert.assertTrue(httpHandler instanceof ScoreUpdateRequestHandler);
    }

    @Test
    public void testHighScoreListURIReturnsHighScoreListRequestHandler() {
        given(httpExchange.getRequestURI()).willReturn(URI.create("/2/highscorelist"));
        given(httpExchange.getRequestMethod()).willReturn("GET");

        HttpHandler httpHandler = factory.getHandler(httpExchange);
        Assert.assertTrue(httpHandler instanceof HighScoreListRequestHandler);
    }

    @Test
    public void testNonMatchingURIReturnsNullHttpHandler() {
        given(httpExchange.getRequestURI()).willReturn(URI.create("/1234/something"));
        given(httpExchange.getRequestMethod()).willReturn("POST");

        HttpHandler httpHandler = factory.getHandler(httpExchange);
        Assert.assertTrue(httpHandler instanceof NullHttpHandler);
    }
}
