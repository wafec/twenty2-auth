package twenty2.auth.api.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twenty2.auth.shared.dto.jwt.JwtHeaderDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class ObjectHashGeneratorImplTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    private void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void whenObjectIsComplete_ShouldReturnHashAndJsonAndContent() throws Exception {
        ObjectHashGeneratorImpl sut = new ObjectHashGeneratorImpl( JwtHeaderDto
                .builder().hashAlg("SHA1").signAlg("RSA")
                .build(), objectMapper, "SHA-1" );

        assertThat( sut.jsonObject(), not( nullValue() ) );
        assertThat( sut.jsonBase64(), not( nullValue() ) );
        assertThat( sut.hash(), not( nullValue() ) );
    }
}
