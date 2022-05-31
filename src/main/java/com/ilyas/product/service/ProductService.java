package com.ilyas.product.service;

import java.util.ArrayList;
import java.util.List;

// import java.util.List;

import com.ilyas.product.model.Product;
import com.ilyas.product.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    
    private ProductRepository productRepository;
    private MongoTemplate mongoTemplate;
    
    @Autowired
    public ProductService(ProductRepository productRepository, MongoTemplate mongoTemplate) {
        this.productRepository = productRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Product> getAllProducts() {
        List<Product> result = productRepository.findAll();
        return result;
    }

    @Transactional
    public void findAndUpdate(Product newProduct) {
        Query query = Query.query(Criteria.where("id").is(newProduct.getId()));
        Update update = new Update().set("price", newProduct.getPrice()).set("amount", newProduct.getAmount());
        mongoTemplate.updateFirst(query, update, Product.class);
    }
}
