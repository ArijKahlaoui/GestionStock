package com.sip.GS.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sip.GS.entities.Provider;

@Repository
public interface ProviderRepository extends CrudRepository<Provider,Long>{

}
