package jschool.dao;

import jschool.model.Category;

import java.util.List;

public interface CategoryDAO {
    Category findByName(String name);
    List<Category> listCategory();
    Category findById(int id);
    void update(Category p);
    void add(Category p);
    Category remove(int id);
}
