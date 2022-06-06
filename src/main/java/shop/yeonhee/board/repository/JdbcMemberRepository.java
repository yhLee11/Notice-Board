package shop.yeonhee.board.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.yeonhee.board.domain.Member;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Optional;

@Repository
public class JdbcMemberRepository implements MemberRepository{
    private static final Logger logger = LoggerFactory.getLogger(JdbcMemberRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Member> findById(String id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        "SELECT * FROM members WHERE member_id = :id",
                        Collections.singletonMap("id", id),
                        memberRowMapper
                )
        );
    }

    private static final RowMapper<Member> memberRowMapper = (resultSet, i) -> {
        String memberId = resultSet.getString("member_id");
        String password = resultSet.getString("member_password");
        return new Member(memberId, password);
    };
}
