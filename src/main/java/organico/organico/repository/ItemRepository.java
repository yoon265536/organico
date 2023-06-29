package organico.organico.repository;

import organico.organico.domain.Item;
import organico.organico.domain.Order;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findById(Long id);

    List<Item> findAll();

    void addCart(Long id);

    List<Item> showCart();

    void saveOrder(String userId);

    List<Order> showOrder(String userId);

    void truncateTableCart();

    Optional<String> searchByUserId(String userId);

    void deleteCartItem(Long id);

    //	public int incrementQuantity(Long id);
//	public int decrementQuantity(Long id);
    void updateQuantity(int quantity, Long id);
}
