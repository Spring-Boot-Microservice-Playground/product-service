package com.ilyas.product.controller;

import java.util.ArrayList;
import java.util.List;

import com.ilyas.product.exception.CustomException;
import com.ilyas.product.model.Product;
import com.ilyas.product.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    ProductService productService;
    @Autowired
    RestTemplate restTemplate;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping
    public ResponseEntity<Slice<Product>> getAllProducts(
        @RequestParam(defaultValue = "0") Integer pageNo, 
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestParam(defaultValue = "id,desc") String[] sortBy
    ){
        try{
            List<Order> orders = new ArrayList<Order>();
            orders.add(new Order(getSortDirection(sortBy[1]), sortBy[0]));
            Slice<Product> result = productService.getAllProducts(pageNo, pageSize, orders);
            return ResponseEntity.ok(result);
        } catch(Exception exception){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Product> addNewProduct(@RequestBody Product product) {
        try {
            Product result = productService.addNewProduct(product);
        return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product newProduct) {
        try {
            productService.findAndUpdate(newProduct);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Slice<Product>> findAllProductsByNameLike(@RequestParam String name, @RequestParam(defaultValue = "1") int pageSize) {
        try {
            Slice<Product> result = productService.findAllProductsByNameLike(name, pageSize);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/test")
    public String restTemplateTesting(){
        String customer = restTemplate.getForObject("http://customer-service/customer/search?name=ilya", String.class);
        return customer;
    }

    @GetMapping("/exception")
    public List<String> customExceptionTest(){
        throw new CustomException("this is custom exception");
        // return "works";
    }
}
