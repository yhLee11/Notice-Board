package shop.yeonhee.board.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class MemberTest {
    @Test
    void 아이디형식_실패_테스트() {
        //given // when //then
        assertThatCode(() -> {
            new Member("s%S", "asd");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 아이디형식_성공_테스트() {
        //given
        Member member = new Member("id", "pass");
        //when //then
        assertThat(member.getMemberId()).isEqualTo("id");
        assertThat(member.getPassword()).isEqualTo("pass");
    }
}