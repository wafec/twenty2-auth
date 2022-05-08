package auth.security;

import auth.shared.dto.jwt.JwtPayloadDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class JwtUserDetails implements UserDetails {
    private final JwtPayloadDto jwtPayloadDto;

    public JwtUserDetails( JwtPayloadDto jwtPayloadDto ) {
        this.jwtPayloadDto = jwtPayloadDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "System.Viewer";
            }
        });
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return jwtPayloadDto.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
