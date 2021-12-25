package com.Kalsym.ResourceServer.service;

import com.Kalsym.ResourceServer.model.Product;
import com.Kalsym.ResourceServer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository repo;

    @Autowired
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> listAll() {
        return repo.findAll();
    }

    public void UpdateProduct(Product p) {
        repo.save(p);
    }

    public void save(Product product) {
        repo.save(product);
    }

    public Product UpdateProduct(int id, Product p) {
        Product existProduct = repo.findById(id).orElseThrow();
        existProduct.setName(p.getName());
        existProduct.setPrice(p.getPrice());
        repo.save(existProduct);
        return existProduct;
    }

    public ResponseEntity<Object> getProductById(Integer product_id) {
        Optional<Product> product = repo.findById(product_id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no product against this Id", HttpStatus.OK);
        }
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}
