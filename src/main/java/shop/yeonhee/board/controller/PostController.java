package shop.yeonhee.board.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import shop.yeonhee.board.ApiResponse;
import shop.yeonhee.board.dto.PostDTO;
import shop.yeonhee.board.service.PostService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ApiResponse<String> notFoundHandler(HttpClientErrorException.NotFound e) {
        return ApiResponse.fail(405, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @GetMapping("article/home/main")
    public ApiResponse<List<PostDTO>> getMain(){
        return ApiResponse.ok(postService.findMain());
    }

    @GetMapping("article/list?boardId={id}")
    public ApiResponse<List<PostDTO>> getPageByBoardId(@PathVariable Long id){
        if(id == 1){
            return ApiResponse.ok(postService.findAdminPage(1));
        }
        return ApiResponse.ok(postService.findUserPage(1));
    }


    @GetMapping("article/list?boardId={id}&page={pageNum}")
    public ApiResponse<List<PostDTO>> getAdminPage(@PathVariable Long id,@PathVariable Integer pageNum){
        if(id == 1){
            return ApiResponse.ok(postService.findAdminPage(pageNum));
        }
        return ApiResponse.ok(postService.findUserPage(pageNum));
    }

    @PostMapping("article/doWrite")
    public ApiResponse<Long> createPost(@RequestBody PostDTO postDTO) {
        Long id = postService.savePost(postDTO);
        return ApiResponse.ok(id);
    }

    @GetMapping("/posts/{title}")
    public ApiResponse<List<PostDTO>> getPost(@PathVariable String title) {
        return ApiResponse.ok(postService.findPostByTitle(title));
    }

    @PostMapping("article/doModify")
    public ApiResponse<PostDTO> updatePost(@RequestBody PostDTO postDTO) throws AccessDeniedException {
        return ApiResponse.ok(postService.updatePost(postDTO));
    }

    @PostMapping("article/doDelete/{id}")
    public ApiResponse<String> deletePost(@PathVariable Long id) {
        return ApiResponse.ok(postService.deletePostById(id));
    }
}
