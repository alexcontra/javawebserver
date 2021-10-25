package com.code.httpserver.http;

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
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateValidTestCase());

        }catch (HttpParseException e){
            fail(e);
        }
        assertNotNull(request);
        assertEquals(request.getMethod(),HttpMethod.GET);
        assertEquals(request.getRequestTarget(),"/");
        assertEquals(request.getOriginalHttpVersion(), "HTTP/1.1");
        assertEquals(request.getBestCompatibleHttpVersion(), HttpVersion.HTTP_1_1);
    }
     @Test
    void parseHttpRequestBadMethod(){
         try {
             HttpRequest request = httpParser.parseHttpRequest(generateBadTestCase1());
            fail("error");
         } catch (HttpParseException e) {
             assertEquals(e.getErrorStatus(),HttpStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED);
         }
    }
    @Test
    void parseHttpRequestBadMethod2(){
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCase2());
            fail("error");
        } catch (HttpParseException e) {
            assertEquals(e.getErrorStatus(),HttpStatusCodes.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }
    @Test
    void parseHttpRequestBadMethod3(){
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCase3());
            fail("error");
        } catch (HttpParseException e) {
            assertEquals(e.getErrorStatus(),HttpStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpRequestBadMethod4(){
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCase4());
            fail("error");
        } catch (HttpParseException e) {
            assertEquals(e.getErrorStatus(),HttpStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpRequestBadMethod5(){
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCase5());
            fail("error");
        } catch (HttpParseException e) {
            assertEquals(e.getErrorStatus(),HttpStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpRequestBadHttpVersion() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadHttpVersionTestCase()
            );
            fail("error");
        } catch (HttpParseException e) {
            assertEquals(e.getErrorStatus(), HttpStatusCodes.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpRequestUnsupportedHttpVersion() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateUnsupportedHttpVersionTestCase()
            );
            fail("error");
        } catch (HttpParseException e) {
            assertEquals(e.getErrorStatus(), HttpStatusCodes.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
    }
    @Test
    void parseHttpRequestSupportedHttpVersion1() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateSupportedHttpVersion1()
            );
            assertNotNull(request);
            assertEquals(request.getBestCompatibleHttpVersion(), HttpVersion.HTTP_1_1);
            assertEquals(request.getOriginalHttpVersion(), "HTTP/1.2");
        } catch (HttpParseException e) {
            fail(e.toString());
        }
    }
    private InputStream generateValidTestCase(){

        //AGAIN THE \r\n WILL BE CRLF FOR THE END OF THE LINE OR THE MESSAGE
        //DOWN HERE WE ARE USING A MODEL OF REQUEST
        String data= "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                data.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }
    private InputStream generateBadTestCase1(){

        //THIS TEST TREATS THE CASE WHEN THE NAME IS NOT EQUAL WITH THE ENUM VALUE WE EXPECT FOR THE NAME ("GET)
        String data= "GeT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n"+
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                data.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }
    private InputStream generateBadTestCase2(){
        //AS YOU CAN SEE THE NAME IS LONGER THAN WE EXPECTED SO WE CATCH THE EXCEPTION BECAUSE OF THE MAX LENGTH IS SMALLER THAN WE READED
        String data= "GETTTTTT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n"+
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                data.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }
    private InputStream generateBadTestCase3(){
        //Request Line Invalid Number Of Items "GET /  AAAAAAA"
        String data= "GET /  AAAAAAA HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n"+
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                data.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }
    private InputStream generateBadTestCase4(){
        //Empty Request Line
        String data= "\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n"+
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                data.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }
    private InputStream generateBadTestCase5(){
        //LINE FEED REMOVED
        String data= "GET / HTTP/1.1\r" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n"+
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                data.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }
    private InputStream generateBadHttpVersionTestCase() {
        String rawData = "GET / HTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
    private InputStream generateUnsupportedHttpVersionTestCase() {
        String rawData = "GET / HTTP/2.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
    private InputStream generateSupportedHttpVersion1() {
        String rawData = "GET / HTTP/1.2\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

}
