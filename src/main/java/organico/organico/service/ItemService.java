package organico.organico.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import organico.organico.domain.Item;
import organico.organico.domain.Order;
import organico.organico.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemService {

    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public Optional<Item> findId(Long id) {
        return repository.findById(id);
    }

    public List<Item> findAll() {
        return repository.findAll();
    }

    public int cartTotalPrice() {
        int totalPrice = 0;
        List<Item> result = repository.showCart();

        for (Item item : result) {
            totalPrice += (item.getPrice() * item.getQuantity());
        }
        log.info("총 결제금액: {}원", totalPrice);

        return totalPrice;
    }

    public int orderTotalPrice(String userId) {
        int orderTotalPrice = 0;
        List<Order> result = repository.showOrder(userId);

        for (Order order : result) {
            orderTotalPrice += (order.getOrderItem().getPrice() * order.getOrderItem().getQuantity());
        }
        log.info("총 결제금액: {}원", orderTotalPrice);

        return orderTotalPrice;
    }

    // 이미 추가된 상품 다시 추가 시 수량 증가해야 함
    // cart 테이블에 동일한 id의 상품이 있는지 조회
    // -> 없으면 addCart() 실행
    // -> 있으면 updateQuantity() 실행
    public void addCart(Long id) {
        List<Item> cart = repository.showCart();
        List<Long> addItemIdList = new ArrayList<>();

        for (Item item : cart) {
            addItemIdList.add(item.getId());
        }

        if (!addItemIdList.contains(id)) {
            repository.addCart(id);
        } else if (addItemIdList.contains(id)) {
            repository.updateQuantity(1, id);
        }
    }

    public List<Item> showCart() {
        return repository.showCart();
    }

    // 사용자가 + 버튼 클릭 시 1, - 버튼 클릭 시 -1을 서버로 보냄 --> quantity가 받음
    public void updateByQuantiy(int quantity, Long id) {
        repository.updateQuantity(quantity, id);
    }

    public void saveOrder(String userId) {
        repository.saveOrder(userId);
        repository.truncateTableCart();
    }

    public List<Item> searchOrder(String userId) {
        Optional<String> result = repository.searchByUserId(userId);

        List<Order> orders = repository.showOrder(userId);
        List<Item> items = new ArrayList<>();

        if (result.isPresent()) {
            for (Order item : orders) {
                items.add(item.getOrderItem());
            }
            return items;
        } else {
            return new ArrayList<Item>();
        }
    }

    public void deleteCartItem(Long id) {
        repository.deleteCartItem(id);
    }
}