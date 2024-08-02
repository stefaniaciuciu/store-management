package com.store.project.controller;

import com.store.project.model.Product;
import com.store.project.modelDTO.ProductDTO;
import com.store.project.service.ProductService;
import com.store.project.util.Util;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.store.project.util.Constants.PRODUCT_CONTROLLER_PATH;

@RestController
@RequestMapping(PRODUCT_CONTROLLER_PATH)
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addNewProduct")
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDTO productDTO) {
        Product product = Util.mapProductDTOtoProduct(productDTO);
        return ResponseEntity.ok(productService.addNewProduct(product));
    }

    @PutMapping("/updateProductQuantity/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, Util.mapProductDTOtoProduct(productDTO)));
    }
}
