package com.store.project.service;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.Product;
import com.store.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product product) {
        Product dbProduct = productRepository.findById(productId)
                .orElseThrow(() -> new CustomExceptions.ProductNotFoundException("Product not found"));

        dbProduct.setQuantity(product.getQuantity());
        dbProduct.setName(product.getName());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setPrice(product.getPrice());

        return productRepository.save(dbProduct);
    }

    public Product updateProductPrice(Double price, Long id) {
        Product dbProduct = productRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ProductNotFoundException("Product not found"));

        dbProduct.setPrice(price);
        return productRepository.save(dbProduct);
    }

    public Product showProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new CustomExceptions.ProductNotFoundException("Product not found"));
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(() -> new CustomExceptions.ProductNotFoundException("Product not found"));
        productRepository.deleteById(id);
    }

}
