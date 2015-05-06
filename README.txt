This projects is an automated railway system for journey planning and route calculations based on graph algorithms
Includes the following functionality (via REST API and web application):
1) calculate total distance for the specified path
2) find all journey options from station to station - based on DFS algorithm
3) find the shortest route from station to station - based on Dijkstra algorithm

The configuration of stations and routes is maintained in a property file (json) 
Admin functionality (add/remove stations/links) is provided via REST API and web application

== Package structure ==

/src/main/java/nz/co/gofetch/railways/ - railway system source code
/src/main/resources/log4j.properties - logging settings
/src/main/config/railway-config.json - railway configuration file
/src/main/config/railway-config.json.backup - backup of the initial railway configuration file
/src/main/webapp - web applications source
/src/test/java - unit tests
doc - Javadoc

Frameworks used:
JUnit http://junit.org/
Jersey RS https://jersey.java.net/
Jackson JSON processor jackson.codehaus.org/
SLF4J logging www.slf4j.org/
D3.js - graph visualisation - d3js.org/
jQuery= https://jquery.com/

== Usage instructions ==
Needs Java/Maven to be built and run

Build/Unit test:
mvn clean install

Create eclipse project (optional):
mvn eclipse:eclipse

REST API and web app can be deployed on Jetty via mvn plugin:
mvn jetty:run

This will start Jetty and run the app on it.

Web app will be available on the following URL:

http://127.0.0.1:8080/gofetch-railways/

API will be available on the following URL:

http://127.0.0.1:8080/gofetch-railways/api/

TODO List:

Admin functionality is not finished and may be not working properly - review API and JS
Enhance and clean up web UI - very ugly and messy at the moment
Review logging and exception handling
Seems to be an issue with circle route - need to test properly