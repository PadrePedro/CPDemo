# CPDemo
CP Demo

This app makes simultaneous network requests to the provided URL.  The number of requests and threads to
process them are configureable.

The app shows all requests being made in a listview.  Each request is shown in one of the following
states:

* Fetch n (request n has yet to be processed, waiting for available thread)
* Fetch n: ...  (request n is being processed by a thread but has not been completed yet)
* Fetch n: HTTP 200, n ms, x bytes (request n has been completed by a thread with associated results)

The listview is updated as each request transitions through the different states.
Touching any request that has been completed will show the response for that request.

