package com.fatec.sigvsemail.service;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.sigvsemail.model.Email;
import com.fatec.sigvsemail.model.EmailRepository;
import com.fatec.sigvsemail.model.StatusEmail;

@Service
public class EmailService {
	Logger logger = LogManager.getLogger(this.getClass());
	final EmailRepository emailRepository;
	final JavaMailSender emailSender;

	public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
		this.emailRepository = emailRepository;
		this.emailSender = emailSender;
	}

	@Value(value = "${spring.mail.username}")
	private String emailFrom;

	@Transactional
	public Email sendEmail(Email email) {
		try {
			email.setSendDateEmail(LocalDateTime.now());
			email.setEmailFrom(emailFrom);
			
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email.getEmailTo());
			message.setSubject(email.getSubject());
			message.setText(email.getText());
			
			emailSender.send(message);
			email.setStatusEmail(StatusEmail.SENT);
		
		} catch (MailException e) {
			logger.info(">>>>> emailservice sendmail erro -> " + e.getMessage());
			email.setStatusEmail(StatusEmail.ERROR);
		} finally {
			return emailRepository.save(email);
		}
	}

}
