package com.fatec.sigvsemail.servico;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
@SpringJUnitConfig
@SpringRabbitTest
class Req01CadastrarClienteTests {
	@Autowired
	private RabbitTemplate template;
	@Test
	void test() {
		// set up the connection
		  CachingConnectionFactory connectionFactory=new CachingConnectionFactory("beaver.rmq.cloudamqp.com");
		  connectionFactory.setUsername("user1");
		  connectionFactory.setPassword("https://test.mosquitto.org/");
		  connectionFactory.setVirtualHost("user1");
		//Recommended settings
		  connectionFactory.setRequestedHeartBeat(30);
		  connectionFactory.setConnectionTimeout(30000);
		//Set up queue, exchanges and bindings
		  RabbitAdmin admin = new RabbitAdmin(connectionFactory);
		  Queue queue = new Queue("default.email");
		  admin.declareQueue(queue);
		  
		  String routingKey = "default.email";
		  AmqpTemplate template = new RabbitTemplate(connectionFactory);
		  //template.convertAndSend("", routingKey,"teste2");
		  
		  String foo = (String) template.receiveAndConvert(routingKey);
		  System.out.println(">>>>>>>>foo -> " + foo);
		  
		 
	}

}
