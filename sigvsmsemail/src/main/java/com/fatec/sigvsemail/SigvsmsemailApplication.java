package com.fatec.sigvsemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SigvsmsemailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigvsmsemailApplication.class, args);
		// Teste para verificar as variáveis de ambiente
        System.out.println("SPRING_MAIL_USERNAME: " + System.getenv("SPRING_MAIL_USERNAME"));
        
	}

}
