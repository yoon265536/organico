//package organico.organico.repository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import lombok.extern.slf4j.Slf4j;
//import organico.organico.domain.Item;
//import organico.organico.domain.Order;
//
//@SpringBootTest
//@Slf4j
//@Transactional
//class ItemRepositoryTest {
//
//	@Autowired private ItemRepository repository;
//
//	@Test
//	void 장바구니담기() {
//		repository.addCart(1L);
//
//
//		assertThat(repository.showCart()).isNotEmpty();
//	}
//
//	@Test
//	void 장바구니조회() {
//		repository.addCart(1L);
//		repository.addCart(3L);
//
//		List<Item> result = repository.showCart();
//
//		for(Item i: result) {
//			log.info("[test cart] test >> item_name : {}, quantity : {}", i.getName(), i.getQuantity());
//		}
//
//		assertThat(result.size()).isEqualTo(2);
//	}
//
//	@Test
//	void 장바구니수량변경() {
//		repository.addCart(1L);
//		repository.addCart(5L);
//
//		repository.updateQuantity(5, 4L);
//
//		List<Item> result = repository.showCart();
//		int totalCount = 0;
//		for(Item i: result) {
//			log.info("[test cart] test >> item_name : {}, quantity : {}", i.getName(), i.getQuantity());
//			totalCount += i.getQuantity();
//		}
//		assertThat(totalCount).isEqualTo(5);
//	}
//
//	@Test
//	void 주문제출() {
//		repository.addCart(5L);
//		repository.saveOrder("홍길동0000");
//
//		assertThat(repository.showOrder("홍길동0000")).isNotEmpty();
//	}
//
//	@Test
//	void 주문조회() {
//		repository.addCart(2L);
//		repository.addCart(4L);
//
//		int beforeOrder = repository.showCart().size();
//
//		repository.saveOrder("홍길동1234");
//
//		List<Order> result = repository.showOrder("홍길동1234");
//
//		for(Order order: result) {
//			log.info("[test order] userId >> {}", order.getUserId());
//			log.info("[test order] itemName >> {}", order.getOrderItem().getName());
//			log.info("[test order] quantity >> {}", order.getOrderItem().getQuantity());
//		}
//
//		assertThat(result.size()).isEqualTo(beforeOrder);
//	}
//}
