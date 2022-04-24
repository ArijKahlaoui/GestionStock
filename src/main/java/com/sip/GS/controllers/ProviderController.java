package com.sip.GS.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sip.GS.entities.Provider;
import com.sip.GS.repositories.ProviderRepository;

@Controller
@RequestMapping("/provider/")
public class ProviderController {
	
	@Autowired
	private ProviderRepository providerRepository;

	@GetMapping("list")
	 //@ResponseBody
	 public String listProviders(Model model) {
		
		 model.addAttribute("providers", providerRepository.findAll());
		
		 return "provider/listProviders";
	 }
	
	@GetMapping("add")
	 public String showAddProviderForm(Model model) {
		 Provider provider = new Provider();// object dont la valeur des attributs par defaut
		 model.addAttribute("provider", provider);
		 return "provider/addProvider";
	 }
	
	
	@PostMapping("add")
	 public String addProvider(@Valid Provider provider, BindingResult result, Model model) {
		 if (result.hasErrors()) {
			 
			 return "provider/addProvider";
		 }
		 
		 providerRepository.save(provider);
		 return "redirect:list";
	 }
	
	@GetMapping("delete/{id}")
	 public String deleteProvider(@PathVariable("id") long id, Model model) {
	
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
	 public String showProviderFormToUpdate(@PathVariable("id") long
			 id, Model model) {
			  Provider provider = providerRepository.findById(id)
			  .orElseThrow(()->new IllegalArgumentException("Invalid  provider Id:" + id));
			  
			  model.addAttribute("provider", provider);
			  
			  return "provider/updateProvider";
			  }
			  
	@PostMapping("update")
	public String updateProvider(@Valid Provider provider, BindingResult result, Model model) {
	
		providerRepository.save(provider);
		return"redirect:list";	  
	}
	
}
