package shop.yeonhee.board.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.yeonhee.board.domain.Post;
import shop.yeonhee.board.domain.PostType;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcPostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void createPost_성공_테스트() {
        //given
        Post post = new Post(1, "admin", PostType.NOTICE, "안녕", "바이", LocalDateTime.now(), LocalDateTime.now());
        //when
        Post createdPost = postRepository.createPost(post);
        //then
        assertThat(createdPost.getPostId()).isEqualTo(post.getPostId());
    }
}