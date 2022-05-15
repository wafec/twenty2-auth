package twenty2.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity( securedEnabled = true )
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfiguration( JwtFilter jwtFilter ) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.cors().and().csrf().disable();

        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );

        http.exceptionHandling().authenticationEntryPoint( ( request, response, exc ) -> {
            response.sendError( HttpServletResponse.SC_UNAUTHORIZED, exc.getMessage() );
        } );

        http.authorizeRequests()
                .anyRequest().permitAll();

        http.addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class );
        http.addFilterBefore( new JwtUserPasswordAuthenticationFilter( authenticationManager() ),
                UsernamePasswordAuthenticationFilter.class );
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials( true );
        configuration.addAllowedHeader( "*" );
        configuration.addAllowedOrigin( "*" );
        configuration.addAllowedMethod( "*" );
        source.registerCorsConfiguration( "/**", configuration );
        return new CorsFilter( source );
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults( "" );
    }
}
