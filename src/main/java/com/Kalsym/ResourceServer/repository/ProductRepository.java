package com.Kalsym.ResourceServer.repository;

import com.Kalsym.ResourceServer.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
