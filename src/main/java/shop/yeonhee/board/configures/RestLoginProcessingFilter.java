package shop.yeonhee.board.configures;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import shop.yeonhee.board.dto.MemberDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private ObjectMapper objectMapper;

    public RestLoginProcessingFilter(){
        super(new AntPathRequestMatcher("/member/doLogin"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!isJson(request)){
            throw new IllegalStateException("It's not a REST request");
        }
        final MemberDTO memberDTO = objectMapper.readValue(request.getReader(), MemberDTO.class);
        if (!StringUtils.hasText(memberDTO.getId()) || !StringUtils.hasText(memberDTO.getPassword())) {
            throw new IllegalStateException("no id or password entered");
        }
        return getAuthenticationManager().authenticate(new RestAuthenticationToken(memberDTO.getId(), memberDTO.getPassword()));
    }

    private boolean isJson(final HttpServletRequest request) {
        return "application/json".equals(request.getHeader("Content-Type"));
    }
}