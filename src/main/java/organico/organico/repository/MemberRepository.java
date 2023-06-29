package organico.organico.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import organico.organico.domain.Member;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    RowMapper<Member> memberRowMapper = (rs, rowNum) -> {
        Member member = new Member();

        member.setMemberName(rs.getString("member_name"));
        member.setMemberId(rs.getString("member_id"));
        member.setPassword(rs.getString("password"));

        return member;
    };

    public MemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // [아이디로 조회]
    public Optional<Member> searchByMemberId(String memberId) {
        String sql = "select * from member where member_id = ?";

        List<Member> result = jdbcTemplate.query(sql, memberRowMapper, memberId);

        return result.stream().findFirst();
    }

    // [가입]
    public Optional<Member> signUp(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingColumns("member_name", "member_id", "password");

        log.info("[repository signUp()] memberId : {}", member.getMemberId());
        log.info("[repository signUp()] memberName : {}", member.getMemberName());
        log.info("[repository signUp()] password : {}", member.getPassword());

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("member_name", member.getMemberName());
        parameter.put("member_id", member.getMemberId());
        parameter.put("password", member.getPassword());

        jdbcInsert.execute(new MapSqlParameterSource(parameter));

        return searchByMemberId(member.getMemberId());
    }
}
