package auth.security;

import auth.shared.core.BearerParser;
import auth.shared.core.TokenParser;
import auth.shared.core.TokenParserFactory;
import auth.shared.dto.jwt.BearerDto;
import auth.shared.exceptions.TokenParserException;
import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final TokenParserFactory tokenParserFactory;
    private final BearerParser bearerParser;

    @Autowired
    public JwtFilter( TokenParserFactory tokenParserFactory,
                      BearerParser bearerParser ) {
        this.tokenParserFactory = tokenParserFactory;
        this.bearerParser = bearerParser;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader( HttpHeaders.AUTHORIZATION );

        try {
            BearerDto bearerDto = bearerParser.parse( authorizationHeader );
            TokenParser tokenParser = tokenParserFactory.build( bearerDto.getContent() );
            JwtUserDetails jwtUserDetails = new JwtUserDetails( tokenParser.payload() );

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    jwtUserDetails, null, jwtUserDetails.getAuthorities() );
            authenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails( request) );

            SecurityContextHolder.getContext().setAuthentication( authenticationToken );
        } catch( TokenParserException | IndexOutOfBoundsException ignored) {

        } finally {
            filterChain.doFilter( request, response );
        }
    }
}
