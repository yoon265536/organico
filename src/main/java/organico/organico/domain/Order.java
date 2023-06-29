package organico.organico.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private String userId;
    private Item orderItem;

    public Order(String userId, Item orderItem) {
        this.userId = userId;
        this.orderItem = orderItem;
    }
}
