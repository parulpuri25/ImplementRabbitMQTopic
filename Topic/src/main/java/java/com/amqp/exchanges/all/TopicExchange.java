package com.amqp.exchanges.all;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.example.ConnectionManager;

public class TopicExchange {

  public static void declareExchange() throws IOException, TimeoutException {
    Channel channel = ConnectionManager.getConnection().createChannel();
    channel.exchangeDeclare("exchange", BuiltinExchangeType.TOPIC, true);
    channel.close();
  }


  public static void declareQueues() throws IOException, TimeoutException {
    Channel channel = ConnectionManager.getConnection().createChannel();

    channel.queueDeclare("Queue1", true, false, false, null);
    channel.queueDeclare("Queue2", true, false, false, null);
    channel.queueDeclare("Queue3", true, false, false, null);
    channel.queueDeclare("Queue4", true, false, false, null);
    channel.queueDeclare("Queue5", true, false, false, null);

    channel.close();
  }

 
  public static void declareBindings() throws IOException, TimeoutException {
    Channel channel = ConnectionManager.getConnection().createChannel();
    
    channel.queueBind("Queue1", "exchange", "queue1.*");
    channel.queueBind("Queue2", "exchange", "queue2.*");
    channel.queueBind("Queue3", "exchange", "queue3.*");
    channel.queueBind("Queue4", "exchange", "queue4.*");
    channel.queueBind("Queue5", "exchange", "queue5.*");
    channel.close();
  }

  public static void main(String[] args) throws IOException, TimeoutException {
    TopicExchange.declareExchange();
    TopicExchange.declareQueues();
    TopicExchange.declareBindings();

    System.out.println("Created Queues and Bindings!!");
  }
}
