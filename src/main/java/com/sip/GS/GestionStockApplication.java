package com.sip.GS;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sip.GS.controllers.ArticleController;

@SpringBootApplication
public class GestionStockApplication {

	public static void main(String[] args) {
		new File(ArticleController.uploadDirectory).mkdir();
		SpringApplication.run(GestionStockApplication.class, args);
	}

}
