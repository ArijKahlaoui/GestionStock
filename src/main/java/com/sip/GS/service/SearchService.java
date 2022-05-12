package com.sip.GS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sip.GS.entities.Article;
import com.sip.GS.entities.Provider;
import com.sip.GS.repositories.ArticleRepository;
import com.sip.GS.repositories.ProviderRepository;

@Service
public class SearchService {
	
	@Autowired
	private ArticleRepository articleRepository; 

	@Autowired
	private ProviderRepository providerRepository; 
	
	public List<Article> getAllArticles(){
		  List<Article> list =  (List<Article>)articleRepository.findAll();
		  return list;
		 }
	
	
	public List<Article> getByKeyword(String keyword){
		  return articleRepository.findByKeyword(keyword);
		 }
	
	
	public List<Provider> getPByKeyword(String keyword){
		  return providerRepository.findByKeyword(keyword);
		 }
}
