package com.sip.GS.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sip.GS.entities.Article;
import com.sip.GS.entities.Provider;
import com.sip.GS.entities.User;
import com.sip.GS.repositories.ArticleRepository;
import com.sip.GS.repositories.ProviderRepository;
import com.sip.GS.repositories.UserRepository;

@Controller
public class HomeController {
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
	public ModelAndView accueil(Model model){
		
		//nbr des articles
		List<Article> articles = (List<Article>) articleRepository.findAll();
		Long nbr = articleRepository.count();
		if(articles.size()==0)
			articles = null;
		 model.addAttribute("articles", articles);
		 model.addAttribute("nbr", nbr);
		 
		 //nbr des provider
		 List<Provider> providers = (List<Provider>) providerRepository.findAll();
			long nbrp = providerRepository.count();
			if(providers.size()==0)
				providers = null;
			  model.addAttribute("providers", providers );
			  model.addAttribute("nbrp", nbrp);
		//nb des users
		List<User> users = (List<User>) userRepository.findAll();
				 long nbrU = userRepository.count();
				 if(users.size()==0)
					 users = null;
				 model.addAttribute("users", users);
				 model.addAttribute("nbrU", nbrU);
		 
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("home");
		return modelAndView;
	 }
	
	
	

}
