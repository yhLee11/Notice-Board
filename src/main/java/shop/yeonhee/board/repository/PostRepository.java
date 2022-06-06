package shop.yeonhee.board.repository;

import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import shop.yeonhee.board.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post createPost(Post post) throws DuplicateKeyException;

    Post updatePost(Post post);

    void deleteById(long postId);

    List<Post> findAll();

    List<Post> findByTitle(String title);

    Optional<Post> findById(Long id);

    List<Post> findPageByMemberId(String memberId);

}
