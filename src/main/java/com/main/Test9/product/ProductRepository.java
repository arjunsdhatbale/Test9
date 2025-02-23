package com.main.Test9.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductMaster, Long>{

	 
	Optional<ProductMaster> findByProductName(String productName); 

	Optional<ProductMaster> findByProductIdOrProductName(Long productId,String productName);
}
