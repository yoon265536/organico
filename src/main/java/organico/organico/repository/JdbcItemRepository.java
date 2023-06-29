package organico.organico.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import organico.organico.domain.Cart;
import organico.organico.domain.Item;
import organico.organico.domain.Order;

import javax.sql.DataSource;
import java.util.*;

@Repository
@Slf4j
public class JdbcItemRepository implements ItemRepository {

    private final JdbcTemplate jdbcTemplate;
    RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
        Item item = new Item();

        item.setId(rs.getLong("id"));
        item.setName(rs.getString("name"));
        item.setInfo(rs.getString("info"));
        item.setPrice(rs.getInt("price"));
        item.setOrigin(rs.getString("origin"));
        item.setIngredient(rs.getString("ingredient"));
        item.setUnit(rs.getString("unit"));
        item.setImgName(rs.getString("imgName"));
        item.setImgPath(rs.getString("imgPath"));

        return item;
    };
    RowMapper<Long> itemIdRowMapper = (rs, rowNum) -> {
        Long itemId;
        itemId = rs.getLong("id");
        return itemId;
    };
    RowMapper<String> userIdRowMapper = (rs, rowNum) -> {
        String userId;
        userId = rs.getString("user_id");
        return userId;
    };
    RowMapper<Cart> cartRowMapper = (rs, rowNum) -> {
        Cart cart = new Cart();
        cart.setId(rs.getLong("id"));
        cart.setQuantity(rs.getInt("quantity"));
        return cart;
    };

    public JdbcItemRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select * from item where id = ?";
        List<Item> result = jdbcTemplate.query(sql, itemRowMapper, id);
        return result.stream().findAny();
    }

    @Override
    public List<Item> findAll() {
        String sql = "select * from item";
        List<Item> result = jdbcTemplate.query(sql, itemRowMapper);
        return result;
    }

    @Override
    public Optional<String> searchByUserId(String userId) {
        String sql = "select user_id from order_list where user_id = ?";
        List<String> userList = jdbcTemplate.query(sql, userIdRowMapper, userId);
        return userList.stream().findFirst();
    }

    // [장바구니] 추가
    // 사용자가 "장바구니에 담기"버튼을 눌렀을 때, 해당 상품의 id를 받음 --> cart 테이블에 저장
    @Override
    public void addCart(Long id) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingColumns("id", "quantity");

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("id", id);
        parameter.put("quantity", 1);

        jdbcInsert.execute(new MapSqlParameterSource(parameter));
    }

    // [장바구니] 조회
    @Override
    public List<Item> showCart() {
        String sql = "select * from cart";
        List<Cart> cartAddList = jdbcTemplate.query(sql, cartRowMapper);

        List<Item> result = new ArrayList<>();
        sql = "select * from item where id = ?";

        for (Cart cart : cartAddList) {
            Item item = jdbcTemplate.queryForObject(sql, itemRowMapper, cart.getId());
            item.setQuantity(cart.getQuantity());
            result.add(item);
        }
        return result;
    }

    // [장바구니] 수량 변경
    // 기존수량 + (quantity)
    @Override
    public void updateQuantity(int quantity, Long id) {
        String sql = "update cart set quantity = quantity + ? where id = ?";
        jdbcTemplate.update(sql, quantity, id);
    }

    // [주문] 제출
    // 장바구니 페이지에서 주문 버튼 클릭 시 사용자가 입력한 user_id 넘어옴
    // cart에 저장되어 있는 id로 item테이블 조회
    // user_id + 조회해서 나온 상품 데이터 order테이블에 저장
    @Override
    public void saveOrder(String userId) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("order_list")
                .usingColumns("id", "user_id", "quantity");

        Map<String, Object> parameter = new HashMap<>();

        for (Item item : showCart()) {
            parameter.put("id", item.getId());
            parameter.put("user_id", userId);
            parameter.put("quantity", item.getQuantity());

            jdbcInsert.execute(new MapSqlParameterSource(parameter));

            log.info("[repository.saveOrder()] user_id : {}, item_name : {}, quantity : {})", userId, item.getName(), item.getQuantity());
        }
    }

    // [주문] 조회
    // 사용자가 주문 시 입력했던 user_id로 order테이블 조회
    // 주문 테이블에서 가져온 id로 item 테이블 조회, Order 객체 생성
    @Override
    public List<Order> showOrder(String userId) {
        List<Order> orderList = new ArrayList<>();

        String searchUserIdSql = "select * from order_list where user_id like ?";
        List<Cart> itemList = jdbcTemplate.query(searchUserIdSql, cartRowMapper, userId);

        String searchItemIdSql = "select * from item where id = ?";
        for (Cart c : itemList) {
            Item item = jdbcTemplate.queryForObject(searchItemIdSql, itemRowMapper, c.getId());
            item.setQuantity(c.getQuantity());
            orderList.add(new Order(userId, item));
        }

        return orderList;
    }

    @Override
    public void truncateTableCart() {
        jdbcTemplate.update("truncate table cart");
    }

    @Override
    public void deleteCartItem(Long id) {
        jdbcTemplate.update("delete from cart where id = ?", id);
    }
}
