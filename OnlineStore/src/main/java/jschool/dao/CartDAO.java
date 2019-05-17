package jschool.dao;

import jschool.model.Cart;
import jschool.model.CartItem;

public interface CartDAO{
        Cart findById(int id);
        CartItem findCartItemByProductId(int id, int cartid);
        void update(Cart c);
        void add(Cart c);
        void remove(Cart ci);
}
