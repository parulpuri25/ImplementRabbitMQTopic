The purpose of this mini project was to work on pub/sub message oriented programming. 
5 data collections were created in MongoDb called as ‘EduCostStatQueryOne’, ‘EduCostStatQueryTwo’, ‘EduCostStatQueryThree’, ‘EduCostStatQueryFour’ and ‘EduCostStatQueryFive’.
These collections are used by the Producer to fetch the data. This mini project adopts a style of message oriented architecture that exchanges messages. The data is retrieved by the producer class from the MongoDb 
collections and publishes it using a routing key. The consumer class will receive the data depending on what topic it is subscribed to.

The server used for the purpose of the project is RabbitMQ server.
The project contains three classes; Producer.java, Subscriber.java and TopicExchange.java and a json file called config.json.
The file config.json declares all the parameters required to make the connection to MongoDb, the routing keys and the parameters required to fetch the data from the MongoDB collections.
Talking about the class Producer.java; it establishes the connection to RabbitMQ server. Then it uses the parameters passed in config.java to fetch the names of connection string and MongoDb database and fetches the
names of all the collections. Further, establish connection between the project and MongoDb. Routing keys are fetched as well using the config.java. 
After all parameters are fetched, the class fetches the data from MongoDb collections and publishes it using the fetched routing key to the subscribed consumer. 

Discussing about Subscriber.java, this class subscribes to 5 different RabbitMQ queues ("Queue1", "Queue2", "Queue3", "Queue4", and "Queue5") and prints out the messages it receives.
