package com.walmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walmart.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	 
}