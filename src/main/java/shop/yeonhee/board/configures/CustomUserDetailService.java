package shop.yeonhee.board.configures;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.yeonhee.board.domain.Member;
import shop.yeonhee.board.repository.MemberRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Could not found user " + id));
        if (member.getMemberId().equals("user1"))
            return User.builder()
                    .username(member.getMemberId())
                    .password(member.getPassword())
                    .roles("USER")
                    .build();

        return User.builder()
                .username(member.getMemberId())
                .password(member.getPassword())
                .roles("ADMIN")
                .build();
    }
}
