package shop.yeonhee.board.repository;

import shop.yeonhee.board.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findById(String id);
}
