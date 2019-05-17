package com.store.display.service;

import com.store.display.dto.ProductRestDTO;

import java.util.List;

/**
 * Interface for product update on the display
 */
public interface ProductService {

    /**
     * Gets list of products
     * @return
     */
    List<ProductRestDTO> getProduct();

    /**
     * Sets list of products
     * @param products list of products
     */
    void setProducts(List<ProductRestDTO> products);

    /**
     * Get new list of products from OnlineStore
     * @return List of update products
     */
    List updateProducts();

    /**
     * Specifies ip for image source server
     * @return
     */
    String getIp();
}
