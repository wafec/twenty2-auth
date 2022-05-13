package auth.security;

import org.springframework.security.core.GrantedAuthority;

public class ClaimedAuthorityImpl implements GrantedAuthority {
    private final String value;

    public ClaimedAuthorityImpl( String value ) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return value;
    }
}
