package com.Kalsym.ResourceServer.controller;

import com.Kalsym.ResourceServer.model.Product;
import com.Kalsym.ResourceServer.service.AuthenticationRequestService;
import com.Kalsym.ResourceServer.service.ProductService;
import com.Kalsym.ResourceServer.utility.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class ProductController {
    private ProductService service;
    @Autowired
    public ProductController(ProductService service ) {
        this.service = service;
    }

    @GetMapping("/products")
    public List<Product> list() {
        return service.listAll();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> get(@PathVariable int id, @RequestHeader String Authorization) throws ParseException {
        if (Authorization.equals("Bearer " + AuthenticationRequestService.accessToken) && DateTime.getDateTime().before(AuthenticationRequestService.TokenExpireTime)) {
            return service.getProductById(id);
        } else if (Authorization.equals("Bearer " + AuthenticationRequestService.accessToken) && DateTime.getDateTime().after(AuthenticationRequestService.TokenExpireTime)) {
            return new ResponseEntity<>("The token is expired", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("You are entering wrong token", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/products")
    public Product add(@RequestBody Product product) {
        service.save(product);
        return product;
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable int id) {
        return new ResponseEntity<Product>(service.UpdateProduct(id, product), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
