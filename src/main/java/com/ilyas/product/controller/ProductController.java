package com.ilyas.product.controller;

import java.util.List;
import java.util.Optional;

import com.ilyas.product.exception.CustomException;
import com.ilyas.product.model.Product;
import com.ilyas.product.repository.ProductRepository;
import com.ilyas.product.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<Slice<Product>> getAllProducts(
        @RequestParam(defaultValue = "0") Integer pageNo, 
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestParam(defaultValue = "id,desc") String[] sortBy
    ) throws Exception {
        Slice<Product> result = productService.getAllProducts(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Product> addNewProduct(@RequestBody Product product)  throws Exception {
        Product result = productRepository.save(product);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product newProduct)  throws Exception {
        productService.findAndUpdate(newProduct);
        return ResponseEntity.ok(newProduct);
    }
    
    @PutMapping("/purchase")
    public ResponseEntity<?> purchaseProduct(@RequestBody Product newProduct) {
        Optional<Product> productRetrieved = productRepository.findById(newProduct.getId());
        Integer updatedAmount = productRetrieved.get().getAmount() - newProduct.getAmount();
        newProduct.setAmount(updatedAmount);
        productService.findAndUpdate(newProduct);
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping("/search")
    public ResponseEntity<Slice<Product>> findAllProductsByNameLike(@RequestParam String name, @RequestParam(defaultValue = "1") int pageSize)  throws Exception {
        Pageable pageable = PageRequest.ofSize(pageSize);
        Slice<Product> result = productRepository.findByName(name, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/test")
    public String restTemplateTesting() throws Exception {
        String customer = restTemplate.getForObject("http://customer-service/customer/search?name=ilya", String.class);
        return customer;
    }

    @GetMapping("/exception")
    public List<String> customExceptionTest(){
        throw new CustomException("this is custom exception");
    }
}
