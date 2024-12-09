package com.fatec.sigvsemail.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fatec.sigvsemail.model.EmailRecordDto;

@Component
public class EmailConsumer {
	@RabbitListener(queues = "${broker.queue.email.name}")
	public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {
		System.out.println(emailRecordDto.emailTo());
	}
}
