package com.fatec.sigvsemail.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * essa classe cria o bean queue utilizada no envio das mensagens
 */
@Configuration
public class RabbitMQConfig {
	Logger logger = LogManager.getLogger(this.getClass());
	@Value("${broker.queue.email.name}")
	private String queue;

	/**
	 * Se a fila já existir e tiver as mesmas propriedades a fila existente será usada, se não existir cria a fila automaticamente
	 * Se as configurações forem diferentes um erro será lançado, indicando que a definição da fila não corresponde à
	 * configuração existente.
	 * o parametro true indica que a fila é persistente, sobrevive a reinicialização do broker
	 * recomendada para filhas de produção ou ambiente criticos
	 * @return
	 */
	@Bean
	public Queue queue() {
		System.out.println(">>>>>>>>>>>> fila->" + queue);
		return new Queue(queue, true);
	}

	/**
	 * Cria o objeto para serializar deserializar objetos de mensagens.
	 * O RabbitMQ envia e recebe mensagens no formato byte[]. Este metodo
	 * converte mensagens automaticamente para objetos Java usando JSON.
	 * Injeção de dependencia - este objeto é mantido no contexto da aplilcacao spring boot (@Bean)
	 * @return
	 */
	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		logger.info(">>>>> rabbitmqconfig msg converter -> converter");
		ObjectMapper objectMapper = new ObjectMapper();
		return new Jackson2JsonMessageConverter(objectMapper);
	}
}
