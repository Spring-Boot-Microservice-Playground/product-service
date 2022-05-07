package com.ilyas.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document
@Getter @Setter
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private Integer price;
    private Integer amount;
}
