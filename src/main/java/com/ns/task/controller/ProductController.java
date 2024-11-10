package com.ns.task.controller;

import com.ns.task.dto.ProductBannerDto;
import com.ns.task.dto.ProductDetailDto;
import com.ns.task.dto.Response;
import com.ns.task.entity.ProductEntity;
import com.ns.task.service.IProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/product")
@AllArgsConstructor
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final IProductService productService;

    @PostMapping(path = "/insert")
    public ResponseEntity<String> insertProduct(@Valid @RequestBody ProductEntity productEntity) {
        logger.info("Inserting new Product");

        logger.error("No error in input fields");
        Response<ProductDetailDto> productDetailDtoResponse = productService.insertProduct(productEntity);
        logger.info(productDetailDtoResponse.getResponseDescription());
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    private ResponseEntity<Response<ProductDetailDto>> getProductByProductId(@PathVariable("id") int id) throws IOException {
        logger.info("Get product by productId");
        Response<ProductDetailDto> productDetailsById = productService.getProductById(id);

        logger.info(productDetailsById.getResponseDescription());
        return new ResponseEntity<>(productDetailsById, HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<Page<ProductBannerDto>> getAllProducts(@RequestParam(defaultValue = "0") int pageNo,
                                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                                  @RequestParam(defaultValue = "productId") String fieldName) {
        logger.info("Get All products");
        Page<ProductBannerDto> allProductDetails = productService.getAllProductDetails(pageNo, pageSize, fieldName);
        if(!allProductDetails.isEmpty() && allProductDetails!=null) {
            logger.info("Sending the product data in pagination");
        } else {
            logger.info("Came to an end of product list or no products found");
        }
        return new ResponseEntity<>(allProductDetails, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productid}")
    private ResponseEntity<Response<String>> deleteProduct(@PathVariable("productid") int id) {
        Response<String> deleteResponse = productService.deleteProduct(id);
        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    private ResponseEntity<Response<?>> updateProduct(@PathVariable("productId") int productId,
                                                        @Valid @RequestBody ProductDetailDto productEntity) {
        logger.info("update product");
        Response<ProductEntity> productEntityResponse = productService.updateProduct(productEntity);
        if (productEntityResponse == null) {
            logger.info(productEntityResponse.getResponseDescription());
        }
        logger.info("Updated successful");
        return new ResponseEntity<>(productEntityResponse, HttpStatus.OK);
    }

//    @PostMapping("/upload/csv-file")
//    private ResponseEntity<Void> uploadCSVFileToDb(@RequestParam("fileService") MultipartFile file) throws IOException {
//        logger.info("CSV file controller");
//        productService.csvFile(file);
//        return new ResponseEntity<>(HttpStatus.OK);
//
//    }

    @GetMapping("/search")
    private ResponseEntity<Page<ProductBannerDto>> searchProducts(@RequestParam(name = "search") String search,
                                                                  @RequestParam(name = "sortOrder") String sortOrder,
                                                                  @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                                  @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("Search product");

        Page<ProductBannerDto> searchResult = productService.search(pageNo, pageSize, search, sortOrder);
        if(searchResult.hasContent()) {
            logger.info("Products found and sending in pagination");
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
        }
       else {
            logger.info(" No products found, list has came to an end");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


}
