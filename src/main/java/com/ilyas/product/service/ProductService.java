package com.ilyas.product.service;

import java.util.List;

// import java.util.List;

import com.ilyas.product.model.Product;
import com.ilyas.product.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    
    public Product addNewProduct(Product product) throws Exception {
        productRepository.save(product);
        return product;
    }

    public Slice<Product> getAllProducts(int pageNo, int pageSize, List<Order> orders) throws Exception {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orders));
        Slice<Product> result = productRepository.findAll(pageable);
        return result;
    }

    @Transactional
    public void findAndUpdate(Product newProduct) throws Exception {
        Query query = Query.query(Criteria.where("id").is(newProduct.getId()));
        Update update = new Update().set("price", newProduct.getPrice()).set("amount", newProduct.getAmount());
        mongoTemplate.updateFirst(query, update, Product.class);
    }

    public Slice<Product> findProductByName(String name, int pageSize) throws Exception {
        Pageable pageable = PageRequest.ofSize(pageSize);
        Slice<Product> result = productRepository.findByName(name, pageable);
        return result;
    }
    // public Long editProduct(Product product){
    //     return productRepository.up;
    // }
}
