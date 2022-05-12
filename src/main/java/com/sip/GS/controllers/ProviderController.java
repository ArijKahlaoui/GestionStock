package com.sip.GS.controllers;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sip.GS.entities.Article;
import com.sip.GS.entities.Provider;
import com.sip.GS.repositories.ProviderRepository;

@Controller
@RequestMapping("/provider/")
public class ProviderController {
	
	public static String uploadDirectory = 
			System.getProperty("user.dir")+"/src/main/resources/static/uploads";
	
	@Autowired
	private ProviderRepository providerRepository;

	@GetMapping("list")
	 //@ResponseBody
	 public String listProviders(Model model) {
		
		List<Provider> providers = (List<Provider>) providerRepository.findAll();
		long nbr = providerRepository.count();
		if(providers.size()==0)
			providers = null;
		  model.addAttribute("providers", providers );
		  model.addAttribute("nbr", nbr);
		 return "provider/listProviders";
	 }
	
	@GetMapping("add")
	 public String showAddProviderForm(Model model) {
		 Provider provider = new Provider();// object dont la valeur des attributs par defaut
		 model.addAttribute("provider", provider);
		 return "provider/addProvider";
	 }
	
	
	@PostMapping("add")
	 public String addProvider(@Valid Provider provider, BindingResult result, Model model,  @RequestParam("files") MultipartFile[] files) {
		 if (result.hasErrors()) {
			 
			 return "provider/addProvider";
		 }
		 
		/// part upload
		 
		 StringBuilder fileName = new StringBuilder();
		 MultipartFile file = files[0];
		 Path fileNameAndPath = Paths.get(uploadDirectory, 
		file.getOriginalFilename());
		 
		 fileName.append(file.getOriginalFilename());
		 try {
			 Files.write(fileNameAndPath, file.getBytes());
		 } catch (IOException e) {
			e.printStackTrace();
		 }
		 provider.setLogo(fileName.toString());
		 
		 
		 providerRepository.save(provider);
		 return "redirect:list";
	 }
	
	@GetMapping("delete/{id}")
	 public String deleteProvider(@PathVariable("id") int id, Model model) {
	
		 //long id2 = 100L;
		 
		 Provider provider = providerRepository.findById(id)
				 .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));
		 
		 System.out.println("suite du programme...");
		 
		 providerRepository.delete(provider);
		 
		 /*model.addAttribute("providers", 
		providerRepository.findAll());
		 return "provider/listProviders";*/
		 return "redirect:../list";
	 }
	
	@GetMapping("edit/{id}")
	 public String showProviderFormToUpdate(@PathVariable("id") int id, Model model)
	 {
			  Provider provider = providerRepository.findById(id)
			  .orElseThrow(()->new IllegalArgumentException("Invalid  provider Id:" + id));
			  
			  model.addAttribute("provider", provider);
			  
			  return "provider/updateProvider";
	  }
			  
	
	@PostMapping("update/{id}")
	public String updateProvider(@PathVariable("id") int id, @Valid Provider provider
			, BindingResult result,Model model)
	{
		if (result.hasErrors()) {
			provider.setId(id);
			return "provider/updateProvider";
		 }
		
		 providerRepository.save(provider);
		 model.addAttribute("providers", providerRepository.findAll());		
		 return "provider/listProviders";
	}
	
	
	@GetMapping("show/{id}")
	public String showProvider(@PathVariable("id") int id, Model model) {
		Provider provider = providerRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));
		List<Article> articles = providerRepository.findArticlesByProvider(id);
		
		for (Article a : articles)
			System.out.println("Article = " + a.getLabel());
		
		model.addAttribute("articles", articles);
		model.addAttribute("provider", provider);
		return "provider/showProvider";
				
	}
	
}
