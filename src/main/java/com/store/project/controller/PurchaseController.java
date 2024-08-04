package com.store.project.controller;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.Purchase;
import com.store.project.modelDTO.PurchaseDTO;
import com.store.project.service.PurchaseService;
import com.store.project.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.store.project.util.Constants.*;

@RestController
@RequestMapping(PURCHASE_CONTROLLER_PATH)
public class PurchaseController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    private final PurchaseService purchaseService;
    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping(GET_ALL_PURCHASES)
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        try {
            return ResponseEntity.ok(purchaseService.getAllPurchases());
        } catch(Exception e) {
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 for any other unexpected errors
        }
    }

    @GetMapping(GET_PURCHASE_BY_ID)
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(purchaseService.getPurchaseById(id));
        } catch(CustomExceptions.PurchaseNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch(Exception e) {
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 for any other unexpected errors
        }
    }

    @PostMapping(ADD_PURCHASE)
    public ResponseEntity<Purchase> addPurchase(@RequestBody PurchaseDTO purchase, @PathVariable Long userId,
                                                @PathVariable Long productId) {
        try {
            return ResponseEntity.ok(purchaseService.addPurchase(Util.mapPurchaseDTOtoPurchase(purchase),
                    userId, productId));
        } catch(Exception e) {
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(VIEW_PURCHASES_BY_USER)
    public ResponseEntity<List<Purchase>> viewAllPurchases(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(purchaseService.viewAllPurchasesByUserId(userId));
        } catch(Exception e) {
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(VIEW_PURCHASES_OF_PRODUCT)
    public ResponseEntity<List<Purchase>> viewAllPurchasesOfProduct(@PathVariable Long productId) {
        try {
            return ResponseEntity.ok(purchaseService.viewAllPurchasesOfProduct(productId));
        } catch(Exception e) {
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
