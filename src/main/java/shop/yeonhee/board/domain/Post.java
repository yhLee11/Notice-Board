package shop.yeonhee.board.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post implements Comparable<Post>{
    private long postId;
    private String memberId;
    private PostType postType;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post(long postId, String memberId, PostType postType, String title, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.memberId = memberId;
        this.postType = postType;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getPostId() {
        return postId;
    }

    public String getMemberId() {
        return memberId;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public int compareTo(Post o) {
        return createdAt.compareTo(o.getCreatedAt());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return postId == post.postId && createdAt.equals(post.createdAt) && updatedAt.equals(post.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, createdAt, updatedAt);
    }
}
