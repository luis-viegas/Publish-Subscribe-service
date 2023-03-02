# Publish-Subscribe service

Developed by:

- Miguel Lopes (up201704590@fe.up.pt)
- Luís Viegas (up201904979@fe.up.pt)
- Mariana Monteiro (up202003480@fe.up.pt)
- Diogo Maia (up201904974@fe.up.pt)

More detailed info [in the report.](./doc/report.pdf)

# Technologies

To compile and run the project, you need to have the following installed:
- Java 17 - https://www.oracle.com/java/technologies/downloads/#java17
- Maven 4.0.0 - https://maven.apache.org/download.cgi

To be sure, these are the specific versions we used. We cannot guarantee that the project will work with other versions.

# Compile

To compile the project, run the following command in the root directory of the project: 

    mvn clean compile

# Run

To run the project, run the following command in the root directory of the project:

    mvn exec:java -Dexec.mainClass="Main" -Dexec.args="<command> <arguments>" -q

Where &lt;command&gt; is one of the following:
- *server* - Starts the server and takes no arguments Eg.:`server`
- *put* - Starts a publisher with id [arg3] and publishes the message [arg2] to the topic [arg1] Eg.: `put Music loveIt 0` 
- *get* - Starts a subscriber with id [arg2] to requests a message from the topic [arg1] Eg.: `get Music 1` 
- *subscribe* - Subscribes a subscriber with id [arg2] to topic [arg1] Eg.: `subscribe Music 1` 
- *unsubscribe* - Unsubscribes a subscriber with id [arg2] to topic [arg1] Eg.: `unsubscribe Music 1` 

And &lt;command&gt; is a collection of 0, 2 or 3 arguments, dependent of the command required.
