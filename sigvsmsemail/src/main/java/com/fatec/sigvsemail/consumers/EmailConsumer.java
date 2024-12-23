package com.fatec.sigvsemail.consumers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fatec.sigvsemail.model.Email;
import com.fatec.sigvsemail.model.EmailRecordDto;
import com.fatec.sigvsemail.service.EmailService;

@Component
public class EmailConsumer {
	Logger logger = LogManager.getLogger(this.getClass());
	final EmailService emailService;
	/**
	 * Objetivo - instanciar o objeto que vai tratar a logica do envio de emails 
	 * injecao de dependencia pelo construtor
	 * @param emailService - servico de email utilizado
	 */
	public EmailConsumer (EmailService emailService) {
		this.emailService = emailService;
	}
	/**
	 * Objetivo - assinar para a lista que a aplicacao deseja ser informada na ocorrencia de novos eventos
	 * na chegada de um evento passa para o servico para persistir e enviar o email.
	 * @param emailRecordDto 
	 */
	@RabbitListener(queues = "${broker.queue.email.name}")
	public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {
		logger.info(">>>>> emailconsumer listen msg recebida -> \n " + emailRecordDto);
		var email = new Email();
		BeanUtils.copyProperties(emailRecordDto, email);//converte dto em model
		emailService.sendEmail(email);
	}
}
