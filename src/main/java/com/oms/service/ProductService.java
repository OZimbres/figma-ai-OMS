package com.oms.service;

import com.oms.dto.CreateProductRequest;
import com.oms.dto.UpdateProductRequest;
import com.oms.model.Product;
import com.oms.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product addProduct(CreateProductRequest request) {
        Product product = new Product(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getPrepTime(),
                request.isAvailable(),
                request.getEmoji()
        );
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(String id, UpdateProductRequest request) {
        return productRepository.findById(id).map(product -> {
            if (request.getName() != null) product.setName(request.getName());
            if (request.getCategory() != null) product.setCategory(request.getCategory());
            if (request.getPrice() != null) product.setPrice(request.getPrice());
            if (request.getPrepTime() != null) product.setPrepTime(request.getPrepTime());
            if (request.getAvailable() != null) product.setAvailable(request.getAvailable());
            if (request.getEmoji() != null) product.setEmoji(request.getEmoji());
            return productRepository.update(product);
        });
    }
}
