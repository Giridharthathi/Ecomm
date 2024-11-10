package com.ns.task.service.impl;

import com.ns.task.dto.*;
import com.ns.task.entity.ProductEntity;
import com.ns.task.entity.ProductReview;
import com.ns.task.entity.UserEntity;
import com.ns.task.mapper.ProductMapper;
import com.ns.task.repository.ProductRepository;
import com.ns.task.service.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {
    @Autowired
    private IProductService productService;
    @Mock
    private ProductRepository productRepository;

    private ProductEntity productEntity;
    private ProductDetailDto productDetailDto;
    @Value("${upload.dir}")
    private String IMAGE_STORAGE_LOCATION;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        ProductReview review = new ProductReview(1,4.0,"TEST_DESCRIPTION", LocalDate.now(),productEntity,new UserEntity());
        productService = new ProductServiceImpl(productRepository);

        productEntity = new ProductEntity(2,"IPhone SE 2020","Apple",20,"Smart Phone",46999,"ELECTRONICS",Arrays.asList(review),0L,0.0);

        productDetailDto = new ProductDetailDto(2,"IPhone SE 2020","Apple",50,"ELECTRONICS",
                        "Smart Phone", 46000.0,null);

    }

    @Test
    void insertNewProduct(){
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        Response<ProductDetailDto> insertResponse = productService.insertProduct(productEntity);
        assertNotNull(insertResponse.getData());
    }
    @Test
    void insertNewProduct_NoImageSave(){
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        Response<ProductDetailDto> insertResponse = productService.insertProduct(productEntity);
        assertNotNull(insertResponse.getData());
    }

    @Test
    void getProductWithProductId() throws IOException {
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(productEntity));
        Response<ProductDetailDto> productById = productService.getProductById(4);
        assertNotNull(productById.getData());
    }

    @Test
    void getProductWithProductId_WhenProductNotFound() throws IOException {
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        Response<ProductDetailDto> productById = productService.getProductById(4);
        assertNull(productById.getData());
    }

    @Test
    void updateProduct_WhenFound(){
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(productEntity));
        ProductEntity dtoToEntityDetails = ProductMapper.INSTANCE.dtoToEntity(productDetailDto);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(dtoToEntityDetails);
        Response<ProductEntity> productEntityResponse = productService.updateProduct(productDetailDto);
        assertNotNull(productEntityResponse.getData());
        assertEquals(50,productEntityResponse.getData().getStock());
    }

    @Test
    void updateProduct_WhenNotFound(){
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(null));
        ProductEntity dtoToEntityDetails = ProductMapper.INSTANCE.dtoToEntity(productDetailDto);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(dtoToEntityDetails);
        Response<ProductEntity> productEntityResponse = productService.updateProduct(productDetailDto);
        assertNull(productEntityResponse.getData());
    }

    @Test
    void deleteProduct(){
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(productEntity));
        Response<String> deleteResponse = productService.deleteProduct(any(Integer.class));
        assertEquals(("Deletion successful"),deleteResponse.getResponseDescription());
    }

    @Test
    void deleteProduct_WhenProductFoundBut_WhenFileNotFound(){
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(productEntity));
        Response<String> deleteResponse = productService.deleteProduct(any(Integer.class));
        assertEquals("File deletion failed",deleteResponse.getResponseDescription());
    }
    @Test
    void deleteProduct_WhenProductNotFound(){
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(null));
        Response<String> deleteResponse = productService.deleteProduct(any(Integer.class));
        assertEquals(("Product not found"),deleteResponse.getResponseDescription());
    }

    @Test
    void getAllProducts(){
        Page<ProductEntity> expectedProducts = new PageImpl<>(Arrays.asList(productEntity,productEntity,productEntity));
        when(productRepository.findAll(any(Pageable.class))).thenReturn((expectedProducts));
        Page<ProductBannerDto> allProductDetails = productService.getAllProductDetails(0, 1, "productId");
        assertNotNull(allProductDetails);
    }

    @Test
    void getAllProducts_NoImages(){
        Page<ProductEntity> expectedProducts = new PageImpl<>(Arrays.asList(productEntity,productEntity,productEntity));
        when(productRepository.findAll(any(Pageable.class))).thenReturn((expectedProducts));
        Page<ProductBannerDto> allProductDetails = productService.getAllProductDetails(0, 1, "productId");
        assertNotNull(allProductDetails);
    }

    @Test
    void search() throws IOException {
        when(productRepository.findBySearch(any(String.class),any(String.class),any(String.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(productEntity)));
        Page<ProductBannerDto> search = productService.search(0, 1, "MOCKSEARCH", "DSC");
        assertNotNull(search);
    }
}