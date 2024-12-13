package com.fatec.sigvsemail.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfig {
	Logger logger = LogManager.getLogger(this.getClass());
	@Value("${broker.queue.email.name}")
	private String queue;

	@Bean
	public Queue queue() {
		return new Queue(queue, true);
	}
	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		logger.info(">>>>> rabbitmqconfig msg converter -> converter");
		ObjectMapper objectMapper = new ObjectMapper();
		return new Jackson2JsonMessageConverter(objectMapper);
	}
}
