package jschool.dao;

import jschool.model.Product;
import java.util.List;

public interface ProductDAO {
     List<Product> list();

     Product findById(int id);

    Product findByIdForUpdate(int id);

    void update(Product p);
     int add(Product p);
     void remove(int id);

    void revive(int id);

    void addImageToProduct(int id, String file);
     List<Product> getProductsByFilter(int minPrice, int maxPrice, int minPlayer, int maxPlayer, int categoryId);
     List<Product> searchByRequest(String name);
}
