package com.code.httpserver.http;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
class HttpParserTest {
    private static HttpParser httpParser;
    @BeforeAll
    public static void BeforeClass(){
        httpParser = new HttpParser();
    }
    @Test
    void parseHttpRequest() throws IOException {
        httpParser.parseHttpRequest(generateValidTestCase());
    }
    private InputStream generateValidTestCase(){

        //AGAIN THE \r\n WILL BE CRLF FOR THE END OF THE LINE OR THE MESSAGE
        //DOWN HERE WE ARE USING A MODEL OF REQUEST FROM MY PERSONAL DEVICE
        String data= "sec-ch-ua-mobile: ?0\r\n" +
                "sec-ch-ua-platform: \"Linux\"\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9,ro;q=0.8";
        InputStream inputStream = new ByteArrayInputStream(
                data.getBytes(StandardCharsets.UTF_8)
        );
        return inputStream;
    }
}