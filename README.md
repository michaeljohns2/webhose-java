# webhose.io client for Java

A simple way to access the [webhose.io](https://webhose.io) API from your Java code

```java
    WebhoseClient client = new WebhoseClient(YOUR_API_KEY);
    for (WebhosePost post : client.search("github").posts) {
        System.out.println(post.title);
    }
```

## API Key

To make use of the webhose.io API, you need to obtain a token that would be
used on every request. To obtain an API key, create an account at
https://webhose.io/auth/signup, and then go into
https://webhose.io/dashboard to see your token.


## Use the API

To get started, you need to import the library, and set your access token.
(Replace YOUR_API_KEY with your actual API key).

```java
    WebhoseClient client = new WebhoseClient(YOUR_API_KEY)
```

Now you can make a request and inspect the results:

```java
    WebhoseResponse r = client.search("foobar");
    System.out.println(r.totalResults);
    // example output: 62

    System.out.println(r.posts.size());
    // example output: 62

    System.out.println(r.posts.get(0).language);
    // example output: english

    System.out.println(r.posts.get(0).title);
    // example output: Putting quotes around dictionary keys in JS
```

If there are more than one page of results, use the `getMore()` method to
fetch the next page.

```java
    r = client.getMore(r);
    System.out.println(r.posts.size())
    // Will output 0 in this case, since there were less than 100 matches
```

The ``getMore()`` method is also useful for fetching new results that were
discovered since the last request.

```java
    WebhoseClient client = new WebhoseClient(YOUR_API_KEY);
    WebhoseResponse r = client.search("minecraft");
    // Fetch new results every 5 minutes
    while(true) {
        try {
            for (WebhosePost post : r.posts) {
                performAction(post);
            }
            Thread.sleep(300000);
            r = client.getMore(r);
            
        } catch (InterruptedException e) {
            Thread.interrupted();
            break;
        }
    }
```

## Full documentation

### WebhoseClient class

* WebhoseClient(token)

  * token - your API key

* search(query)

  * query - the search query, either as a search string, or as a Query object

### WebhoseQuery class

WebhoseQuery objects correspond to the advanced search options that appear on https://webhose.io/use

WebhoseQuery objects have the following members:

* ``allTerms`` - a list of strings, all of which must appear in the results
* ``someTerms`` - a list of strings, some of which must appear in the results
* ``phrase`` - a phrase that must appear verbatim in the results
* ``exclude`` - terms that should not appear in the results
* ``siteType`` - one or more of ``discussions``, ``news``, ``blogs``
* ``language`` - one or more of language names, in lowercase english
* ``site`` - one or more of site names, top level only (i.e., yahoo.com and not news.yahoo.com)
* ``title`` - terms that must appear in the title
* ``bodyText`` - term that must appear in the body text

Query objects implement the ``toString()`` method, which shows the resulting search string.

### WebhoseResponse class

Response objects have the following members:

* ``totalResults`` - the total number of posts which match this search
* ``moreResultsAvailable`` - the number of posts not included in this response
* ``posts`` - a list os Post objects
* ``next`` - a URL for the next results page for this search

Response objects implement the ``__iter__()`` method, which can be used to loop
over all posts matching the query. (Automatic page fetching)

### WebhosePost and WebhoseThread classes

WebhosePost and WebhoseThread object contain the actual data returned from the
API. Consult https://webhose.io/documentation to find out about their structure.

