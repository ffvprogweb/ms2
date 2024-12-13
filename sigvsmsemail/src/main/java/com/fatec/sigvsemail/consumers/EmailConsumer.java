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
	public EmailConsumer (EmailService emailService) {
		this.emailService = emailService;
	}
	@RabbitListener(queues = "${broker.queue.email.name}")
	public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {
		//System.out.println(emailRecordDto.emailTo());
		logger.info(">>>>> emailconsumer listen msg recebida do usuarioId-> " + emailRecordDto.usuarioId());
		var email = new Email();
		BeanUtils.copyProperties(emailRecordDto, email);//converte dto em model
		emailService.sendEmail(email);
	}
}
