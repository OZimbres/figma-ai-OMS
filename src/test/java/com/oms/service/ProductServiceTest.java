package com.oms.service;

import com.oms.dto.CreateProductRequest;
import com.oms.dto.UpdateProductRequest;
import com.oms.model.Product;
import com.oms.model.enums.ProductCategory;
import com.oms.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productService = new ProductService(productRepository);

        productRepository.save(new Product("p1", "Espresso", ProductCategory.DRINKS, 2.50, 2, true, "\u2615"));
        productRepository.save(new Product("p2", "Cappuccino", ProductCategory.DRINKS, 3.50, 3, true, "\u2615"));
    }

    @Test
    void getAllProducts() {
        List<Product> products = productService.getAllProducts();
        assertEquals(2, products.size());
    }

    @Test
    void getProductById() {
        Optional<Product> product = productService.getProductById("p1");
        assertTrue(product.isPresent());
        assertEquals("Espresso", product.get().getName());
    }

    @Test
    void getProductByIdNotFound() {
        Optional<Product> product = productService.getProductById("nonexistent");
        assertTrue(product.isEmpty());
    }

    @Test
    void addProduct() {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Latte");
        request.setCategory(ProductCategory.DRINKS);
        request.setPrice(4.00);
        request.setPrepTime(3);
        request.setAvailable(true);
        request.setEmoji("\u2615");

        Product created = productService.addProduct(request);
        assertNotNull(created.getId());
        assertEquals("Latte", created.getName());
        assertEquals(3, productService.getAllProducts().size());
    }

    @Test
    void updateProduct() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setPrice(3.00);
        request.setAvailable(false);

        Optional<Product> updated = productService.updateProduct("p1", request);
        assertTrue(updated.isPresent());
        assertEquals(3.00, updated.get().getPrice());
        assertFalse(updated.get().isAvailable());
        assertEquals("Espresso", updated.get().getName());
    }

    @Test
    void updateProductNotFound() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setPrice(5.00);

        Optional<Product> updated = productService.updateProduct("nonexistent", request);
        assertTrue(updated.isEmpty());
    }
}
