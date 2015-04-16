package com.buzzilla.webhose;

import com.buzzilla.webhose.client.WebhoseClient;
import com.buzzilla.webhose.client.WebhosePost;
import com.buzzilla.webhose.client.WebhoseResponse;

import java.io.IOException;

/**
 * Simple example usage of the WebhoseClient class
 *
 */
public class Example
{
    public static void main( String[] args ) throws IOException {
        if (args.length < 1) {
            System.err.println("No API Key was provided");
            System.exit(1);
        }

        WebhoseClient client = new WebhoseClient(args[0]);

        WebhoseResponse response = client.search("site:telegraph.co.uk is_first:true");
        for (WebhosePost post : response.posts) {
            System.out.println(post.author);
        }
    }
}
