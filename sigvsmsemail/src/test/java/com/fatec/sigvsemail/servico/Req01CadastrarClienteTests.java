package com.fatec.sigvsemail.servico;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * O que pode falhar - erro de autenticacao, erro de comunicação sem internet
 * erro da administracao da fila (fila nao declarada, erro na rota, consequencia
 * msg nao recebida) verificacao de estado X verificacao do comportamento
 * (valida o estado e o comportamento portanto mais detalhado uso de mocks)
 */
//@SpringJUnitConfig configura o Mockito 
@SpringRabbitTest
class Req01CadastrarClienteTests {
	@Autowired
	private RabbitTemplate template;

	@Test
	void ct01_quando_cliente_valido_email_enviado_com_sucesso() {
		try {
			// set up the connection - o que pode falhar erro de autenticacao, erro de
			// comunicação

			CachingConnectionFactory connectionFactory = new CachingConnectionFactory("beaver.rmq.cloudamqp.com");
			connectionFactory.setUsername("usuario1");
			connectionFactory.setPassword("mqtt.org.br");
			connectionFactory.setVirtualHost("usuario1");
			assertNotNull(connectionFactory.createConnection(), "A conexão com o RabbitMQ não foi estabelecida.");

			// Recommended settings
			connectionFactory.setRequestedHeartBeat(30); // intervalo de envio de sinais de vida (heartbeats) entre o
															// cliente e o broker
			connectionFactory.setConnectionTimeout(30000); // tempo maximo para estabelecer uma conexão com o broker
			// Set up queue, exchanges and bindings - cria uma classe de gerenciamento de
			// recursos
			// permite recuperar propriedades da fila, como numero de mensagens pendentes ou
			// consumidores ativos
			RabbitAdmin admin = new RabbitAdmin(connectionFactory);
			// Queue queue = new Queue("default.email");
			// admin.declareQueue(queue);
			assertTrue(admin.getQueueProperties("default.email") != null, "A fila não foi criada corretamente.");
			String routingKey = "default.email";
			AmqpTemplate template = new RabbitTemplate(connectionFactory);
			template.convertAndSend("", routingKey, "teste2");
			System.out.println(">>>>>>>> mensagem enviada");

			String msg_recebida = (String) template.receiveAndConvert(routingKey);
			System.out.println(">>>>>>>> mensagem recebida -> " + msg_recebida);
		} catch (Exception e) {
			fail("erro no processamento ->" + e.getMessage());
		}

	}

}
