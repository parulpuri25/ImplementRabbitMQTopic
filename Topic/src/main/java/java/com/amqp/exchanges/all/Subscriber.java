package com.amqp.exchanges.all;


import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.*;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Subscriber {

	public static void main(String[] args) throws IOException, TimeoutException {
	    ConnectionFactory factory = new ConnectionFactory();
	    Connection connection = factory.newConnection("http://localhost:15672");
	    Channel channel = connection.createChannel();

	    DeliverCallback deliverCallback = (consumerTag, message1) -> {
	      System.out.println(consumerTag);
	      System.out.println(new String(message1.getBody(), "UTF-8"));
	    };

	    CancelCallback cancelCallback = consumerTag -> {
	      System.out.println(consumerTag);
	    };
	    
	    channel.basicConsume("Queue1", true, deliverCallback, cancelCallback);
	    
	    channel.basicConsume("Queue2", true, deliverCallback, cancelCallback);
	    
	    channel.basicConsume("Queue3", true, deliverCallback, cancelCallback);
	    
	    channel.basicConsume("Queue4", true, deliverCallback, cancelCallback);
	    
	    channel.basicConsume("Queue5", true, deliverCallback, cancelCallback);

	}

}
