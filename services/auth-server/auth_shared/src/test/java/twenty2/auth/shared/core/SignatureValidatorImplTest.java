package twenty2.auth.shared.core;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twenty2.auth.shared.cryptography.Decrypter;
import twenty2.auth.shared.dto.jwt.JwtHeaderDto;
import twenty2.auth.shared.exceptions.CryptographyException;
import twenty2.auth.shared.exceptions.SignatureValidatorException;

import java.security.Security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
public class SignatureValidatorImplTest {
    @Mock
    private PublicKeyManager publicKeyManager;

    @Mock
    private Decrypter decrypter;

    private SignatureValidatorImpl signatureValidator;

    @BeforeEach
    private void setUp() {
        Security.addProvider( new BouncyCastleProvider() );
        signatureValidator = new SignatureValidatorImpl( publicKeyManager, decrypter );
    }

    @Test
    void testValidate_WhenSignatureIsEqual_ShouldReturnTrue() throws Exception {
        byte[] decryptedData = new byte[ 100 ];
        byte[] hashData = new byte[100];
        when( decrypter.decrypt( any(), any(), any() ) ).thenReturn( decryptedData );
        ObjectHashParser objectHashParser = mock( ObjectHashParser.class );
        when( objectHashParser.hash( any() ) ).thenReturn( hashData );

        boolean result = signatureValidator.validate( mock( JwtHeaderDto.class ), objectHashParser, "" );

        assertThat( result, is( true ) );
    }

    @Test
    void testValidate_WhenSignatureIsNotEqual_ShouldReturnFalse() throws Exception {
        byte[] decryptedData = new byte[ 100 ];
        byte[] hashData = new byte[ 100 ];
        hashData[ 0 ] = 1;
        when( decrypter.decrypt( any(), any(), any() ) ).thenReturn( decryptedData );
        ObjectHashParser objectHashParser = mock( ObjectHashParser.class );
        when( objectHashParser.hash( any() ) ).thenReturn( hashData );

        boolean result = signatureValidator.validate( mock( JwtHeaderDto.class ), objectHashParser, "" );

        assertThat( result, is( false ) );
    }

    @Test
    void testValidate_WhenPublicKeyIsInvalid_ShouldThrowAnException() throws Exception {
        doThrow( CryptographyException.class ).when( decrypter ).decrypt( any(), any(), any() );
        JwtHeaderDto jwtHeaderDto = mock( JwtHeaderDto.class );
        ObjectHashParser objectHashParser = mock( ObjectHashParser.class );

        Assertions.assertThrows( SignatureValidatorException.class, () -> signatureValidator
                .validate( jwtHeaderDto, objectHashParser, "" ) );
    }
}
