package com.ilyas.product.service;
import com.ilyas.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public void findAndUpdate(Product newProduct) {
        Query query = Query.query(Criteria.where("id").is(newProduct.getId()));
        Update update = new Update().set("price", newProduct.getPrice()).set("amount", newProduct.getAmount());
        mongoTemplate.updateFirst(query, update, Product.class);
    }
}
