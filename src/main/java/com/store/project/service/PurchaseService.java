package com.store.project.service;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.Product;
import com.store.project.model.Purchase;
import com.store.project.model.User;
import com.store.project.repository.ProductRepository;
import com.store.project.repository.PurchaseRepository;
import com.store.project.repository.UserRepository;
import static com.store.project.util.Constants.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    Logger logger = LogManager.getLogger(PurchaseService.class);

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository,
                           ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id).orElseThrow(() -> {
            logger.error(PURCHASE_NOT_FOUND);
            return new CustomExceptions.PurchaseNotFoundException(PURCHASE_NOT_FOUND);
        });
    }

    public Purchase addPurchase(Purchase purchase, Long userId, Long productId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error(USER_NOT_FOUND);
                    return new CustomExceptions.UserNotFoundException(USER_NOT_FOUND);
                });
        purchase.setUser(user);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error(PRODUCT_NOT_FOUND);
                    return new CustomExceptions.ProductNotFoundException(PRODUCT_NOT_FOUND);
                });
        purchase.setProduct(product);

        purchase.setPrice(purchase.getQuantity()*product.getPrice());

        return purchaseRepository.save(purchase);
    }

    public List<Purchase> viewAllPurchasesByUserId(Long userId) {
        return purchaseRepository.findByUserId(userId);
    }

    public List<Purchase> viewAllPurchasesOfProduct(Long productId) {
        return purchaseRepository.findByProductId(productId);
    }
}
