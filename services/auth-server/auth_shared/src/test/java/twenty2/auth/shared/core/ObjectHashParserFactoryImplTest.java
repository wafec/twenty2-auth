package twenty2.auth.shared.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.shared.exceptions.ObjectHashParserException;
import twenty2.auth.shared.models.SimpleModel;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class ObjectHashParserFactoryImplTest {
    @Mock
    private ObjectMapper objectMapper;

    private ObjectHashParserFactoryImpl sut;

    private static final String TEXT_ENCODED = "dGVzdA==";

    private static Stream<String> provideInvalidEncodedStrings() {
        return Stream.of( null, "", "abcdefg123" );
    }

    @BeforeEach
    private void setup() {
        sut = new ObjectHashParserFactoryImpl( objectMapper );
    }

    @Test
    void whenBuildingNormalInputInstance_ShouldReturnValidObject() throws Exception {
        when( objectMapper.readValue( anyString(), eq( SimpleModel.class ) ) )
                .then( Answers.RETURNS_DEEP_STUBS );

        ObjectHashParser<SimpleModel> result = sut.build( SimpleModel.class, TEXT_ENCODED );

        assertThat( result, is( notNullValue() ) );
    }

    @ParameterizedTest
    @MethodSource( "provideInvalidEncodedStrings" )
    void whenBuildingInvalidInputInstance_ShouldThrowAnException( final String encodedString ) {
        sut = new ObjectHashParserFactoryImpl( new ObjectMapper() );

        Assertions.assertThrows( ObjectHashParserException.class, () -> sut.build( SimpleModel.class, encodedString ) );
    }
}
