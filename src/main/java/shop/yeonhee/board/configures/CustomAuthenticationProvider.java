package shop.yeonhee.board.configures;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UserDetails userDetails = userDetailService.loadUserByUsername(authentication.getName());
        final String encryptedPassword = userDetails.getPassword();
        final String enteredPassword = (String) authentication.getCredentials();

        if (!passwordEncoder.matches(encryptedPassword, enteredPassword)) {
            throw new BadCredentialsException("password not matched");
        }
        return new RestAuthenticationToken(userDetails.getAuthorities(), null, userDetails.getUsername());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RestAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
