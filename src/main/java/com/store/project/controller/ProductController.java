package com.store.project.controller;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.Product;
import com.store.project.modelDTO.ProductDTO;
import com.store.project.service.ProductService;
import com.store.project.util.Util;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.store.project.util.Constants.PRODUCT_CONTROLLER_PATH;

@RestController
@RequestMapping(PRODUCT_CONTROLLER_PATH)
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addNewProduct")
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product product = Util.mapProductDTOtoProduct(productDTO);
            return ResponseEntity.ok(productService.addNewProduct(product));
        } catch(Exception e) {
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, Util.mapProductDTOtoProduct(productDTO)));
        } catch(CustomExceptions.ProductNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch(Exception e) {
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/updateProduct/price/{price}/{id}")
    public ResponseEntity<Product> updateProductPrice(@PathVariable Double price, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.updateProductPrice(price, id));
        } catch(CustomExceptions.ProductNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch(Exception e) {
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/showProduct/{id}")
    public ResponseEntity<Product> showProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.showProduct(id));
        } catch(CustomExceptions.ProductNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch(Exception e) {
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch(CustomExceptions.ProductNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch(Exception e) {
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 for any other unexpected errors
        }
    }


}
