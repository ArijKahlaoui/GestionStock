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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sip.GS.entities.Article;
import com.sip.GS.entities.Provider;
import com.sip.GS.repositories.ArticleRepository;
import com.sip.GS.repositories.ProviderRepository;
import com.sip.GS.service.SearchService;



@Controller
@RequestMapping("/article/")
public class ArticleController {

	public static String uploadDirectory = 
			System.getProperty("user.dir")+"/src/main/resources/static/uploads";
	
	private final ArticleRepository articleRepository;
	
	private final ProviderRepository providerRepository;
	
	@Autowired
	 private SearchService service;
	
	@Autowired
	public ArticleController(ArticleRepository articleRepository, ProviderRepository providerRepository) {
		this.articleRepository = articleRepository;
		this.providerRepository = providerRepository; 
	}
	
	@GetMapping("list")
	 public String listProviders(Model model) {
	 
		List<Article> articles = (List<Article>) articleRepository.findAll();
		Long nbr = articleRepository.count();
		if(articles.size()==0)
			articles = null;
		 model.addAttribute("articles", articles);
		 model.addAttribute("nbr", nbr);
		 return "article/listArticles";
	 }
	
	
	@GetMapping("add")
	 public String showAddArticleForm(Article article, Model model) {
		model.addAttribute("providers", providerRepository.findAll());
		 model.addAttribute("article", new Article());
		 return "article/addArticle";
	}

	
	@PostMapping("add")
	 //@ResponseBody
	 public String addArticle(@Valid Article article, BindingResult result, 
	@RequestParam(name = "providerId", required = true) int p, @RequestParam("files") MultipartFile[] files) {
	 
	 Provider provider = providerRepository.findById(p)
	 .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + p));
	
	 article.setProvider(provider);
	 
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
		 article.setPicture(fileName.toString());
	 articleRepository.save(article);
	 return "redirect:list";
	 }


	@GetMapping("delete/{id}")
	 public String deleteProvider(@PathVariable("id") int id, Model model) {
	 Article artice = articleRepository.findById(id)
	 .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));
	 
	 articleRepository.delete(artice);
	 model.addAttribute("articles", articleRepository.findAll());
	 
	 return "article/listArticles";
	 }
	 
	 @GetMapping("edit/{id}")
	 public String showArticleFormToUpdate(@PathVariable("id") int id, Model model) {
	
	 Article article = articleRepository.findById(id)
			 .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + id));
	 
	 model.addAttribute("article", article);
	 model.addAttribute("providers", providerRepository.findAll());
	 model.addAttribute("idProvider", article.getProvider().getId());
	 
	 return "article/updateArticle";
	 }
	 
	 
	 @PostMapping("edit/{id}")
	 public String updateArticle(@PathVariable("id") int id, @Valid Article article, BindingResult result,
	
		 Model model, @RequestParam(name = "providerId", required = false) int p, @RequestParam("files") MultipartFile[] files) {
		 
		 if (result.hasErrors()) {
		 article.setId(id);
		 
		 return "article/updateArticle";
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
		 article.setPicture(fileName.toString());
		 
		 
		 Provider provider = providerRepository.findById(p)
				 .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + p));
		 article.setProvider(provider);
		 
		 articleRepository.save(article);
		 model.addAttribute("articles", articleRepository.findAll());
		 
		 return "article/listArticles";
	 }
	 
	 @GetMapping("show/{id}")
	 public String showArticleDetails(@PathVariable("id") int id, Model model) {
	
		 Article article = articleRepository.findById(id)
				 .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + id));
		 
		 model.addAttribute("article", article);
		 
		 return "article/showArticle";
	 }

	
	 @RequestMapping(value={"/search"}, method = RequestMethod.GET)
	 public String search(Article article, Provider provider,Model model, String keyword) {
	  if(keyword != null) {
		  List<Article> list = service.getByKeyword(keyword);
		  List<Provider> listP = service.getPByKeyword(keyword);
		  model.addAttribute("list", list);
		  model.addAttribute("listP", listP);
	  }
	  else { List<Article> list = service.getAllArticles();
		 model.addAttribute("list", list); 
	  }
		  return "article/findArticles";
	 }
}
