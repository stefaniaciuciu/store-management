package com.store.project.controller;

import com.store.project.model.Purchase;
import com.store.project.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.store.project.util.Constants.PURCHASE_CONTROLLER_PATH;

@RestController
@RequestMapping(PURCHASE_CONTROLLER_PATH)
public class PurchaseController {

    private final PurchaseService purchaseService;
    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/getAllPurchases")
    public List<Purchase> getAllPurchases() {
        try {
            return ResponseEntity.ok(purchaseService.getAllPurchases());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
