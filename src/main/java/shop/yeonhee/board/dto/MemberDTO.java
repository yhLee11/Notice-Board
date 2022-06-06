package shop.yeonhee.board.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDTO {
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    private String id;
    private String password;

    private MemberDTO(final String id,final String password) {
        this.id = id;
        this.password = password;
    }

    public static MemberDTO of(final String id, final String password) {
        return new MemberDTO(id, password);
    }
}
