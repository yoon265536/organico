package organico.organico.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import organico.organico.domain.Item;
import organico.organico.service.ItemService;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //각 카테고리 페이지로 이동
    @GetMapping("/items/{pageName}")
    public String itemPage(@PathVariable("pageName") String pageName, Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        log.info("pageName >> {}", pageName);
        return String.format("items/%s", pageName);
    }

    //상품 상세 페이지
    @GetMapping("/detail/detail_item/{id}")
    public String detailPage(@PathVariable Long id, Model model) {
        Optional<Item> item = itemService.findId(id);
        log.info("id >> {}", id);
        model.addAttribute("item", item.get());
        return "detail/detail_item";
    }

    //상품담기
    @PostMapping("/addCart")
    public String addToCart(@RequestParam Long id, RedirectAttributes attributes) {
        itemService.addCart(id);
        attributes.addAttribute("id", id);
        return "redirect:/detail/detail_item/{id}";
    }

    //장바구니
    @GetMapping("/cart")
    public String showCart(Model model) {
        List<Item> cartItems = itemService.showCart();
        int totalPrice = itemService.cartTotalPrice();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", "총 결제금액 : " + totalPrice + "원");
        return "cart";
    }

    //장바구니 수량
    @PostMapping("/cart/update/{quantity}")
    public String updateCartItemQuantity(@PathVariable int quantity, @RequestParam Long id) {
        itemService.updateByQuantiy(quantity, id);
        return "redirect:/cart";
    }

    //장바구니 상품 삭제
    @PostMapping("/cart/delete")
    public String deleteCartItem(@RequestParam("id") Long id) {
        itemService.deleteCartItem(id);
        return "redirect:/cart";
    }

    //장바구니 주문
    @PostMapping("/cart/order")
    public String submitOrder(@RequestParam("userId") String userId, RedirectAttributes attributes) {
        itemService.saveOrder(userId);
        attributes.addAttribute("userId", userId);
        return "redirect:/order";
    }

    //주문 조회
    @GetMapping("/order")
    public String showOrder(Model model, @RequestParam("userId") String userId) {
        log.info("userId >> {}", userId);
        int orderTotalPrice = itemService.orderTotalPrice(userId);
        int orderTotalPriceDelivery = itemService.orderTotalPrice(userId) + 3000;
        List<Item> orders = itemService.searchOrder(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("orders", orders);
        model.addAttribute("orderTotalPrice", orderTotalPrice);
        model.addAttribute("orderTotalPriceDelivery", orderTotalPriceDelivery);

        if (0 == orders.size()) {
            return "/order/error";
        }
        return "order";
    }


}
