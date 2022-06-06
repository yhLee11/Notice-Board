package shop.yeonhee.board.configures;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String errMsg = "Email or password is invalid";
        if (exception instanceof BadCredentialsException) {
            errMsg = "Email or password is invalid";
        }
        objectMapper.writeValue(response.getWriter(), HttpStatus.UNAUTHORIZED);
    }
}
