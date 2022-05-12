package com.sip.GS.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sip.GS.entities.Article;



public interface ArticleRepository extends CrudRepository<Article, Integer>{
	@Query(value = "select * from article  a where a.label like %:keyword%", nativeQuery = true)
	 List<Article> findByKeyword(@Param("keyword") String keyword);
}
