package com.amqp.exchanges.all;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	public static void main(String[] args) throws IOException, TimeoutException {
		 ConnectionFactory factory = new ConnectionFactory();
		 Connection connection = factory.newConnection("http://localhost:15672");
		 Channel channel = connection.createChannel();
         
	
         String contents = new String(Files.readAllBytes(Paths.get("config.json")));

         Document config = Document.parse(contents);

         Document mongoConfig = (Document) config.get("mongoDB");
         String connectionString = mongoConfig.getString("host");
         String databaseName = mongoConfig.getString("database");
         String col1 = mongoConfig.get("collections",Document.class).getString("ColQueryOne");
         String col2 = mongoConfig.get("collections",Document.class).getString("ColQueryTwo");
         String col3 = mongoConfig.get("collections",Document.class).getString("ColQueryThree");
         String col4 = mongoConfig.get("collections",Document.class).getString("ColQueryFour");
         String col5 = mongoConfig.get("collections",Document.class).getString("ColQueryFive");
         // Create a connection to MongoDB
         MongoClient client = MongoClients.create(connectionString);
         MongoDatabase database = client.getDatabase(databaseName);
         MongoCollection<Document> collection1 = database.getCollection(col1);
         MongoCollection<Document> collection2 = database.getCollection(col2);
         MongoCollection<Document> collection3 = database.getCollection(col3);
         MongoCollection<Document> collection4 = database.getCollection(col4);
         MongoCollection<Document> collection5 = database.getCollection(col5);
         //Fetch the routing keys
         String routingKey1 = config.get("RoutingKeys", Document.class).getString("routingKey1");
         String routingKey2 = config.get("RoutingKeys", Document.class).getString("routingKey2");
         String routingKey3 = config.get("RoutingKeys", Document.class).getString("routingKey3");
         String routingKey4 = config.get("RoutingKeys", Document.class).getString("routingKey4");
         String routingKey5 = config.get("RoutingKeys", Document.class).getString("routingKey5");
         
		 // Fetch the Data from MongoDb Collection
         Document query1 = (Document) config.get("EduCostStatQueryOne");
                  
         FindIterable<Document> result1 = collection1.find(query1);
         List<Document> resultList1 = new ArrayList<>();
         for (Document doc : result1) {
             resultList1.add(doc);
         }
         
         for (Document doc : resultList1) {
     	    byte[] data1 = doc.toJson().getBytes("UTF-8");
     	    channel.basicPublish("exchange", routingKey1, null, data1);
     	}     
         
         Document query2 = (Document) config.get("EduCostStatQueryTwo");
        
         FindIterable<Document> result2 = collection2.find(query2);
         List<Document> resultList2 = new ArrayList<>();
         for (Document doc : result2) {
             resultList2.add(doc);
             
         }
         
         for (Document doc : resultList2) {
        	    byte[] data2 = doc.toJson().getBytes("UTF-8");
        	    channel.basicPublish("exchange", routingKey2, null, data2);
        	}
         
         Document query3 = (Document) config.get("EduCostStatQueryThree");
         
         FindIterable<Document> result3 = collection3.find(query3);
         List<Document> resultList3 = new ArrayList<>();
         for (Document doc : result3) {
             resultList3.add(doc);
         }
        
         for (Document doc : resultList3) {
        	    byte[] data3 = doc.toJson().getBytes("UTF-8");
        	    channel.basicPublish("exchange",routingKey3 , null, data3);
        	}
         
         
         Document configDoc = (Document) config.get("EduCostStatQueryFour");
         int pastYears = Integer.parseInt(configDoc.getString("Range of PastYears"));

         Document query4 = new Document("Range of PastYears", pastYears);
         
         FindIterable<Document> result4 = collection4.find(query4);

         List<Document> resultList4 = new ArrayList<>();
         for (Document doc : result4) {
             resultList4.add(doc);
            
         }
        
         for (Document doc : resultList4) {
        	    byte[] data4 = doc.toJson().getBytes("UTF-8");
        	    channel.basicPublish("exchange", routingKey4, null, data4);
        	}
         
         
         Document query5 = (Document) config.get("EduCostStatQueryFive");
         
         FindIterable<Document> result5 = collection5.find(query5);
         List<Document> resultList5 = new ArrayList<>();
         for (Document doc : result5) {
             resultList5.add(doc);
         }
         
         for (Document doc : resultList5) {
        	    byte[] data5 = doc.toJson().getBytes("UTF-8");
        	    channel.basicPublish("exchange", routingKey5, null, data5);
        	}
         
		    
		    //Close the channel and connection
		    channel.close();
		    connection.close();

	}

}
