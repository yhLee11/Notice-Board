package shop.yeonhee.board.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class Member {
    private static final Logger logger = LoggerFactory.getLogger(Member.class);
    private String memberId;
    private String password;

    public Member(String memberId, String password) {
        if (!checkMemberId(memberId)) {
            logger.error("Invalid ID format", new IllegalArgumentException());
            throw new IllegalArgumentException();
        }
        this.memberId = memberId;
        this.password = password;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getPassword() {
        return password;
    }

    private static boolean checkMemberId(String memberId) {
        return Pattern.matches("^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{0,29}$", memberId);
    }
}
