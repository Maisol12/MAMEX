package mx.edu.utez.mamex.models.cart;
import mx.edu.utez.mamex.models.items.Item;
import mx.edu.utez.mamex.models.cart.CartItem;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void clear() {
        this.items.clear();
    }


    public boolean addItem(Item item, int quantity) {
        // Esto es solo un ejemplo, tu implementación puede variar
        return items.add(new CartItem(item, quantity));
    }

    public boolean removeItem(Item item) {
        // Esto es solo un ejemplo, tu implementación puede variar
        return items.removeIf(cartItem -> cartItem.getItem().equals(item));
    }

    public boolean isEmpty() {
        // Retorna true si la lista de ítems es null o está vacía
        return items == null || items.isEmpty();
    }
}
