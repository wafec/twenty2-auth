package twenty2.auth.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtUserPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public JwtUserPasswordAuthenticationFilter() {
        this( null );
    }

    public JwtUserPasswordAuthenticationFilter( AuthenticationManager authenticationManager ) {
        super( authenticationManager );
        setPostOnly( false );
    }
}
