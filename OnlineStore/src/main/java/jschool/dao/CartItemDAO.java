package jschool.dao;

import jschool.model.CartItem;


public interface CartItemDAO {
    void addCartItem(CartItem p);
    void updateCartItem(CartItem p);
    void removeCartItemByID(CartItem id);
}
