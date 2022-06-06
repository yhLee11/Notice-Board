package shop.yeonhee.board.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import shop.yeonhee.board.domain.Post;
import shop.yeonhee.board.domain.PostType;
import shop.yeonhee.board.dto.PostDTO;
import shop.yeonhee.board.repository.PostRepository;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public List<PostDTO> findMain() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOS = new ArrayList<>();
        posts.sort(Post::compareTo);
        for (Post p : posts) {
            if (postDTOS.size() >= 10)
                break;
            postDTOS.add(new PostDTO(p.getPostId(), p.getTitle(), p.getContents()));
        }
        return postDTOS;
    }

    public List<PostDTO> findAdminPage(Integer pageNum) {
        List<Post> posts = postRepository.findPageByMemberId("admin");
        List<PostDTO> postDTOS = new ArrayList<>();
        posts.sort(Post::compareTo);
        for (int i = (pageNum - 1) * 10; i < pageNum * 10; i++) {
            if (posts.size() <= i)
                break;
            postDTOS.add(new PostDTO(posts.get(i).getPostId(), posts.get(i).getTitle(), posts.get(i).getContents()));
        }
        return postDTOS;
    }

    public List<PostDTO> findUserPage(Integer pageNum) {
        List<Post> posts = postRepository.findPageByMemberId("user1");
        List<PostDTO> postDTOS = new ArrayList<>();
        posts.sort(Post::compareTo);
        for (int i = (pageNum - 1) * 10; i < pageNum * 10; i++) {
            if (posts.size() <= i)
                break;
            postDTOS.add(new PostDTO(posts.get(i).getPostId(), posts.get(i).getTitle(), posts.get(i).getContents()));
        }
        return postDTOS;
    }

    public List<PostDTO> findPostByTitle(String title) {
        List<Post> posts = postRepository.findByTitle(title);
        return posts.stream().map(p -> new PostDTO(p.getPostId(), p.getTitle(), p.getContents())).collect(Collectors.toList());
    }

    public Long savePost(PostDTO postDTO) {
        var principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        if (username.equals("admin"))
            return postRepository.createPost(new Post(postDTO.getPostId(), username, PostType.NOTICE, postDTO.getTitle(), postDTO.getContents(), LocalDateTime.now(), LocalDateTime.now())).getPostId();
        return postRepository.createPost(new Post(postDTO.getPostId(), username, PostType.FREE, postDTO.getTitle(), postDTO.getContents(), LocalDateTime.now(), LocalDateTime.now())).getPostId();
    }

    public PostDTO updatePost(PostDTO postDTO) throws AccessDeniedException {
        var principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        Post post = postRepository.findById(postDTO.getPostId()).orElseThrow();
        if (username.equals("admin")){
            throw new AccessDeniedException("admin is not updated post");
        }
        if (post.getMemberId().equals(username)){
            Post updatedPost = postRepository.updatePost(new Post(postDTO.getPostId(), username, PostType.FREE, post.getTitle(), post.getContents(), post.getCreatedAt(), LocalDateTime.now()));
            return new PostDTO(updatedPost.getPostId(), updatedPost.getTitle(), updatedPost.getContents());
        }else{
            throw new AccessDeniedException("username is not matched");
        }
    }

    public String deletePostById(Long id) {
        postRepository.deleteById(id);
        return "delete complete";
    }
}
