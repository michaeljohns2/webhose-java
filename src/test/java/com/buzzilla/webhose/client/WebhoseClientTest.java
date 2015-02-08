package com.buzzilla.webhose.client;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author itsadok
 */
public class WebhoseClientTest {
    @Test
    public void testSimpleRequest() throws IOException {
        WebhoseClient.HTTP_TRANSPORT = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {
                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(200);
                        response.setContentType("application/json");
                        response.setContent("{\"totalResults\":123}");
                        return response;
                    }
                };
            }
        };


        WebhoseClient client = new WebhoseClient("test-api-key");
        WebhoseResponse apple = client.search("apple");
        assertThat(apple.totalResults, is(123));
    }
}
