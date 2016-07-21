# Koji Java Interface

This is a Java API for talking to [Koji](https://fedorahosted.org/koji/wiki).

## Dependencies

* [jHTTPc](https://github.com/commonjava/jhttpc) (Apache httpclient wrapper with SSL certificate-handling improvements, etc.)
* [RWX](https://github.com/commonjava/RWX) (XML-RPC libraries offering flexibility in client choice, annotation-based data binding, etc.)
* [Atlas](https://github.com/commonjava/atlas) (Object model and related utilities for working with Maven artifacts)

## Developing New Kojiji Commands

Currently, the best success I've had in implementing Koji commands within Kojiji uses the following method:

### 1. Capture the Messages Involved

Run the command using the Koji command-line client, with `--debug-xmlrpc` enabled, and save it to a log file:


```
$ koji --debug-xmlrpc list-tags 2>&1 | tee list-tags.log

send: 'POST /kojihub HTTP/1.1\r\nHost: REDACTED\r\nAccept-Encoding: identity\r\nUser-Agent: koji/1.7\r\nContent-Type: text/xml\r\nContent-Length: 107\r\n\r\n'
send: "<?xml version='1.0'?>\n<methodCall>\n<methodName>getAPIVersion</methodName>\n<params>\n</params>\n</methodCall>\n"
reply: 'HTTP/1.1 200 OK\r\n'
header: Date: Thu, 21 Jul 2016 20:36:29 GMT
header: Server: Apache/2.2.15 (Red Hat)
header: Strict-Transport-Security: max-age=63072000; includeSubDomains
header: Content-Length: 121
header: Connection: close
header: Content-Type: text/xml
body: "<?xml version='1.0'?>\n<methodResponse>\n<params>\n<param>\n<value><int>1</int></value>\n</param>\n</params>\n</methodResponse>\n"
send: 'POST /kojihub HTTP/1.1\r\nHost: REDACTED\r\nAccept-Encoding: identity\r\nUser-Agent: koji/1.7\r\nContent-Type: text/xml\r\nContent-Length: 178\r\n\r\n'
send: "<?xml version='1.0'?>\n<methodCall>\n<methodName>listTags</methodName>\n<params>\n<param>\n<value><nil/></value></param>\n<param>\n<value><nil/></value></param>\n</params>\n</methodCall>\n"
reply: 'HTTP/1.1 200 OK\r\n'
header: Date: Thu, 21 Jul 2016 20:36:30 GMT
header: Server: Apache/2.2.15 (Red Hat)
header: Strict-Transport-Security: max-age=63072000; includeSubDomains
header: Content-Length: 5693545
header: Connection: close
header: Content-Type: text/xml
body: "<?xml version='1.0'?>\n<methodResponse>\n
[...]
```

### 2. Split the Messages into Exchanges

Use the `koji-output-tidy.py` helper script in `research/` to generate a series of message-exchange files in a new folder:


```
$ mkdir research/list-tags
$ ./research/koji-output-tidy.py list-tags.log research/list-tags/list-tags

Opening temp/list-tags-1.txt
Closing temp/list-tags-1.txt (10 lines)
Opening temp/list-tags-2.txt
Closing temp/list-tags-2.txt (717 lines)
```

3. Implement the Model Classes

For each file in the output directory, implement the appropriate object-model classes in the `com.redhat.red.build.koji.model.xmlrpc.messages` package, using the RWX bindings, to model the XML request and response. Here's part of an example message exchange file containing some of the `list-tags` result from above:


```
<?xml version='1.0'?>
<methodCall>
<methodName>listTags</methodName>
<params>
<param>
<value><nil/></value></param>
<param>
<value><nil/></value></param>
</params>
</methodCall>

HTTP/1.1 200 OK

Date: Thu, 21 Jul 2016 20:36:30 GMT
Server: Apache/2.2.15 (Red Hat)
Strict-Transport-Security: max-age=63072000; includeSubDomains
Content-Length: 5693545
Connection: close
Content-Type: text/xml
<?xml version='1.0'?>
<methodResponse>
<params>
<param>
<value><array><data>
<value><struct>
<member>
<name>maven_support</name>
<value><boolean>0</boolean></value>
</member>
<member>
<name>locked</name>
<value><boolean>0</boolean></value>
</member>
<member>
<name>name</name>
<value><string>my-tag</string></value>
</member>
<member>
<name>perm</name>
<value><nil/></value></member>
<member>
<name>id</name>
<value><int>1</int></value>
</member>
<member>
<name>arches</name>
<value><string>i386</string></value>
</member>
<member>
<name>maven_include_all</name>
<value><boolean>0</boolean></value>
</member>
[...]
```

For this example, the corresponding object-model classes are [com.redhat.red.build.koji.model.xmlrpc.messages.ListTagsRequest](https://github.com/release-engineering/kojiji/blob/master/src/main/java/com/redhat/red/build/koji/model/xmlrpc/messages/ListTagsRequest.java) and [com.redhat.red.build.koji.model.xmlrpc.messages.ListTagsResponse](https://github.com/release-engineering/kojiji/blob/master/src/main/java/com/redhat/red/build/koji/model/xmlrpc/messages/ListTagsResponse.java).

### Factor Out Common Payload Sections

Many requests / responses will contain the same types of information. These should be factored out into common object-model classes that can be reused between message class types. These live in the `com.redhat.red.build.koji.model.xmlrpc` package. For instance, multiple Koji calls may return build information as part of the response. This information is captured by the class [com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo](https://github.com/release-engineering/kojiji/blob/master/src/main/java/com/redhat/red/build/koji/model/xmlrpc/KojiBuildInfo.java).

### Write Unit Tests

You already have sample XML for the request and response messages that Koji expects. From here, you can turn them into unit tests that verify round-trip serialization / deserialization to XML for your new model classes. By chopping up each message exchange into its constituent XML documents and saving them in `src/test/resources`, you can implement something like the following minimal set of unit tests:

* [com.redhat.red.build.koji.model.xmlrpc.messages.ListTagsRequestTest](https://github.com/release-engineering/kojiji/blob/master/src/test/java/com/redhat/red/build/koji/model/xmlrpc/messages/ListTagsRequestTest.java)
* [com.redhat.red.build.koji.model.xmlrpc.messages.ListTagsResponseTest](https://github.com/release-engineering/kojiji/blob/master/src/test/java/com/redhat/red/build/koji/model/xmlrpc/messages/ListTagsResponseTest.java)

### TO BE DOCUMENTED: Integration Testing

Unit tests are fine for verifying compatibility with a message protocol from a given point in time, but what happens if that message protocol changes? The only way to be really certain of compatibility with a particular version of Koji is to run tests that talk to a live instance. We have some of these tests already, which use a suite of Docker containers at build time to provision a Koji environment for testing purposes. After the build completes, these instances are torn down and discarded (after logs and other files pertinent to the test results have been retrieved, of course). To support this, we're using a project called [koji-dojo](https://github.com/release-engineering/koji-dojo).

While this is a great way to be absolutely certain of compatibility, it is definitely labor intensive. It takes quite a bit of work to define the initial state of the system and then load it just in time for a test to use it. And beyond test data management, it becomes important to be able to simulate certain actions within the Koji environment (like builds taking place) in order to ensure the database is in a valid state. This is where we need to go next, and some of Kojiji's commands don't have integration tests now because we have yet to mock the logic that Koji's builder daemon uses to register a completed build.
