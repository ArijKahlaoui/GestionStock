package com.sip.GS.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sip.GS.entities.Article;
import com.sip.GS.entities.Provider;

@Repository
public interface ProviderRepository extends CrudRepository<Provider,Integer>{

	@Query("FROM Article a WHERE a.provider.id = ?1")
	List<Article> findArticlesByProvider(int id);
	
	
	@Query(value = "select * from provider  a where a.name like %:keyword%", nativeQuery = true)
	 List<Provider> findByKeyword(@Param("keyword") String keyword);
	
	
	
	
}
