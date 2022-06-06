package shop.yeonhee.board.dto;

public class PostDTO {
    private long postId;
    private String title;
    private String contents;

    public PostDTO(long postId, String title, String contents) {
        this.postId = postId;
        this.title = title;
        this.contents = contents;
    }

    public long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
