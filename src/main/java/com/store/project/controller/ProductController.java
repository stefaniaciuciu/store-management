package com.store.project.controller;

import com.store.project.exceptions.CustomExceptions;
import com.store.project.model.Product;
import com.store.project.modelDTO.ProductDTO;
import com.store.project.service.ProductService;
import com.store.project.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.store.project.util.Constants.*;

@RestController
@RequestMapping(PRODUCT_CONTROLLER_PATH)
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(ADD_NEW_PRODUCT)
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDTO productDTO) {

        Product product = Util.mapProductDTOtoProduct(productDTO);
        return ResponseEntity.ok(productService.addNewProduct(product));
    }

    @PutMapping(UPDATE_PRODUCT)
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProduct(id, Util.mapProductDTOtoProduct(productDTO)));
    }

    @PatchMapping(UPDATE_PRODUCT_PRICE)
    public ResponseEntity<Product> updateProductPrice(@PathVariable Double price, @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProductPrice(price, id));
    }

    @GetMapping(SHOW_PRODUCT_BY_ID)
    public ResponseEntity<Product> showProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.showProduct(id));
    }

    @DeleteMapping(DELETE_PRODUCT)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
