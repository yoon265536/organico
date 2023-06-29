package organico.organico.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import organico.organico.domain.Item;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class AdminRepository {

    private final JdbcTemplate jdbcTemplate;
    RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
        Item item = new Item();

        item.setId(rs.getLong("id"));
        item.setName(rs.getString("name"));
        item.setPrice(rs.getInt("price"));
        item.setOrigin(rs.getString("origin"));
        item.setIngredient(rs.getString("ingredient"));
        item.setImgName(rs.getString("imgName"));
        item.setImgPath(rs.getString("imgPath"));

        return item;
    };

    public AdminRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<Item> findById(Long id) {
        String sql = "select * from item where id = ?";
        List<Item> result = jdbcTemplate.query(sql, itemRowMapper, id);
        return result.stream().findAny();
    }

    public Item save(Item item) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("item")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameter = new HashMap<>();

        parameter.put("name", item.getName());
        parameter.put("info", item.getInfo());
        parameter.put("price", item.getPrice());
        parameter.put("origin", item.getOrigin());
        parameter.put("ingredient", item.getIngredient());
        parameter.put("unit", item.getUnit());
        parameter.put("imgName", item.getImgName());
        parameter.put("imgPath", item.getImgPath());

        Number id = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameter));
        item.setId(id.longValue());

        log.info("imgPath : {}", item.getImgPath());

        return item;
    }

    public void deleteItem(Long id) {
        String sql = "delete from item where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Item updateItem(Long id, Item item) {
        String sql = "update item set name = ?, info = ? ,price = ?, origin = ?, ingredient = ?, unit = ? where id = ?";
        jdbcTemplate.update(sql, item.getName(), item.getInfo(), item.getPrice(),
                item.getOrigin(), item.getIngredient(), item.getUnit(), id);
        return this.findById(id).get();
    }
}
