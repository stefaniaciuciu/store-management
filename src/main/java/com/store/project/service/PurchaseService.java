package com.store.project.service;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.Product;
import com.store.project.model.Purchase;
import com.store.project.model.User;
import com.store.project.repository.ProductRepository;
import com.store.project.repository.PurchaseRepository;
import com.store.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id).orElseThrow(() ->
                new CustomExceptions.PurchaseNotFoundException("Purchase not found"));
    }

    public Purchase addPurchase(Purchase purchase, Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptions.UserNotFoundException("User not found"));
        purchase.setUser(user);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomExceptions.ProductNotFoundException("Product not found"));
        purchase.setProduct(product);

        return purchaseRepository.save(purchase);
    }


}
