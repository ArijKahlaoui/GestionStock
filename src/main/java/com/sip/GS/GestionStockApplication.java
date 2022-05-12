package com.sip.GS;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sip.GS.controllers.ArticleController;
import com.sip.GS.controllers.LoginController;
import com.sip.GS.controllers.ProviderController;

@SpringBootApplication
public class GestionStockApplication  {

	/*
	 * @Autowired private JavaMailSender javaMailSender;
	 * 
	 * void sendEmail() { SimpleMailMessage msg = new SimpleMailMessage();
	 * msg.setTo("springboot000@gmail.com");
	 * msg.setSubject("Testing from Spring Boot");
	 * msg.setText("Hello World \n Spring Boot Email"); javaMailSender.send(msg); }
	 * 
	 * @Override public void run(String... args) throws MessagingException,
	 * IOException { System.out.println("Sending Email..."); sendEmail();
	 * System.out.println("Done"); }
	 */

	public static void main(String[] args) {
		new File(ArticleController.uploadDirectory).mkdir();
		new File(ProviderController.uploadDirectory).mkdir();
		new File(LoginController.uploadDirectory).mkdir();
		SpringApplication.run(GestionStockApplication.class, args);
	}

}
