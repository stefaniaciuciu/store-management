package com.store.project.service;

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
        try {
            Product dbProduct = productRepository.findById(productId).get();

            dbProduct.setQuantity(product.getQuantity());
            dbProduct.setName(product.getName());
            dbProduct.setDescription(product.getDescription());
            dbProduct.setPrice(product.getPrice());

            return productRepository.save(dbProduct);
        }
        catch (Exception e) {
            return null;
        }
    }


}
