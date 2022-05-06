package com.ilyas.product.repository;

import com.ilyas.product.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Query(value = "{name: {$regex: /?0*/, $options: 'si'}}")
    Page<Product> findByName(String name, Pageable pageable);
    Page<Product> findAll(Pageable pageable);
}
