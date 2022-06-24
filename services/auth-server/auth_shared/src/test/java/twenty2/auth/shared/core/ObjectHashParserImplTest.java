package twenty2.auth.shared.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import twenty2.auth.shared.exceptions.ObjectHashParserException;
import twenty2.auth.shared.models.SimpleModel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ObjectHashParserImplTest {
    private static final String TEXT_ENCODED = "dGVzdA==";

    @Test
    void testGetInstance_WhenEncodedStringIsValid_ShouldReturnInstance() throws Exception {
        ObjectMapper objectMapper = mock( ObjectMapper.class );
        when( objectMapper.readValue( anyString(), eq( SimpleModel.class ) ) )
                .thenReturn( SimpleModel.builder().build() );
        ObjectHashParserImpl<SimpleModel> sut = new ObjectHashParserImpl<SimpleModel>(
                SimpleModel.class, TEXT_ENCODED, objectMapper );

        SimpleModel model = sut.getInstance();

        assertThat( model, is( notNullValue() ) );
    }

    @Test
    void testInitialization_WhenEncodedStringIsNotValid_ShouldThrowAnException() {
        ObjectMapper objectMapper = new ObjectMapper();

        assertThrows( ObjectHashParserException.class, () ->
                new ObjectHashParserImpl<SimpleModel>( SimpleModel.class, null, objectMapper ) );
    }
}
