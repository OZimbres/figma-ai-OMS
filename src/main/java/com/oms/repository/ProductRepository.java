package com.oms.repository;

import com.oms.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {
    private final ConcurrentHashMap<String, Product> products = new ConcurrentHashMap<>();

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Optional<Product> findById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    public Product save(Product product) {
        products.put(product.getId(), product);
        return product;
    }

    public Product update(Product product) {
        products.put(product.getId(), product);
        return product;
    }

    public void delete(String id) {
        products.remove(id);
    }
}
