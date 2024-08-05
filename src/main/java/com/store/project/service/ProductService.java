package com.store.project.service;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.Product;
import com.store.project.repository.ProductRepository;
import static com.store.project.util.Constants.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    Logger logger = LogManager.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product product) {
        var dbProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error(PRODUCT_NOT_FOUND);
                    return new CustomExceptions.ProductNotFoundException(PRODUCT_NOT_FOUND);
                });

        dbProduct.setQuantity(product.getQuantity());
        dbProduct.setName(product.getName());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setPrice(product.getPrice());

        return productRepository.save(dbProduct);
    }

    public Product updateProductPrice(Double price, Long id) {
        var dbProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error(PRODUCT_NOT_FOUND);
                    return new CustomExceptions.ProductNotFoundException(PRODUCT_NOT_FOUND);
                });

        dbProduct.setPrice(price);
        return productRepository.save(dbProduct);
    }

    public Product showProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            logger.error(PRODUCT_NOT_FOUND);
            return new CustomExceptions.ProductNotFoundException(PRODUCT_NOT_FOUND);
        });
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(() -> {
            logger.error(PRODUCT_NOT_FOUND);
            return new CustomExceptions.ProductNotFoundException(PRODUCT_NOT_FOUND);
        });
        productRepository.deleteById(id);
    }

}
