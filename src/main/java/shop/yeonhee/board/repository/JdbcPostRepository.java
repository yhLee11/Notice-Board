package shop.yeonhee.board.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.yeonhee.board.domain.Post;
import shop.yeonhee.board.domain.PostType;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcPostRepository implements PostRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcPostRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Post> postRowMapper = (resultSet, i) -> {
        long postId = resultSet.getLong("post_id");
        String memberId = resultSet.getString("member_id");
        PostType postType = PostType.valueOf(resultSet.getString("postType"));
        String title = resultSet.getString("title");
        String contents = resultSet.getString("contents");
        var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        var updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Post(postId, memberId, postType, title, contents, createdAt, updatedAt);
    };

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    private Map<String, Object> toParaMap(Post post) {
        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("postId", post.getPostId());
        paraMap.put("title", post.getTitle());
        paraMap.put("postType", post.getPostType().toString());
        paraMap.put("contents", post.getContents());
        paraMap.put("memberId", post.getMemberId());
        paraMap.put("createdAt", post.getCreatedAt());
        paraMap.put("updatedAt", post.getUpdatedAt());
        return paraMap;
    }

    public JdbcPostRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Post createPost(Post post) {
        try {
            int update = jdbcTemplate.update(
                    "insert into posts(post_id, member_id, post_type, title, contents, created_at, updated_at)"
                            + " VALUES(:postId, :memberId, :postType, :title, :contents, :createdAt, :updatedAt)",
                    toParaMap(post));
            if (update != 1) {
                throw new IllegalArgumentException("Nothing was inserted");
            }
        } catch (IllegalArgumentException e) {
            logger.error("Nothing was inserted", e);
            throw new IllegalArgumentException();
        } catch (DuplicateKeyException e) {
            logger.error("Key is duplicated", e);
            throw new DuplicateKeyException("Key is duplicated");
        }
        return post;
    }

    @Override
    public Post updatePost(Post post) {
        try {
            int update = jdbcTemplate.update("UPDATE posts SET title=:title, contents=:contents,updated_at=:updatedAt WHERE post_id=:postId",
                    toParaMap(post));
            if (update != 1) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            logger.error("Nothing was deleted.", e);
            throw new IllegalArgumentException();
        }
        return post;
    }

    @Override
    public void deleteById(long postId) {
        try {
            int update = jdbcTemplate.update("DELETE FROM posts WHERE post_id =:postId",
                    Collections.singletonMap("postId", postId));
            if (update != 1) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            logger.error("Nothing was deleted.", e);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Post> findAll() {
        return jdbcTemplate.query("SELECT * FROM posts", Collections.emptyMap(), postRowMapper);
    }

    @Override
    public List<Post> findByTitle(String title) {
        return jdbcTemplate.query(
                "SELECT * FROM posts WHERE title LIKE '%:title%'",
                Collections.singletonMap("title", title),
                postRowMapper);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM posts WHERE member_id = :id",
                Collections.singletonMap("id", id),
                postRowMapper));
    }

    @Override
    public List<Post> findPageByMemberId(String memberId) {
        return jdbcTemplate.query("SELECT * FROM posts WHERE member_id = :memberId",
                Collections.singletonMap("memberId", memberId), postRowMapper);
    }

}
