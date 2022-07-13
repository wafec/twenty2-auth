package twenty2.auth.api.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twenty2.auth.shared.dto.jwt.JwtHeaderDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class ObjectHashGeneratorFactoryImplTest {
    private ObjectMapper objectMapper;
    private ObjectHashGeneratorFactoryImpl sut;

    private static final String ALGORITHM = "SHA-1";

    @BeforeEach
    private void setup() {
        objectMapper = new ObjectMapper();
        sut = new ObjectHashGeneratorFactoryImpl( objectMapper, ALGORITHM );
    }

    @Test
    void whenCreating_ShouldReturnInstance() throws Exception {
        Object result = sut.build( JwtHeaderDto.builder().build() );

        assertThat( result, not( nullValue() ) );
    }

    @Test
    void whenGettingAlgorithm_ShouldReturnFixed() {
        String algorithm = sut.algorithm();

        assertThat( algorithm, equalTo( ALGORITHM ) );
    }
}
