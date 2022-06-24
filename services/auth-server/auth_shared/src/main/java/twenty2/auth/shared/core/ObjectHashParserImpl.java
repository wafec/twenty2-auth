package twenty2.auth.shared.core;

import twenty2.auth.shared.exceptions.ObjectHashParserException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Base64;

public class ObjectHashParserImpl <T> implements ObjectHashParser <T> {
    private final String jsonBase64;
    private final ObjectMapper objectMapper;
    private final Class<T> classType;
    private T objInstance;
    private String jsonString;

    public ObjectHashParserImpl( Class<T> classType, String jsonBase64, ObjectMapper objectMapper )
            throws ObjectHashParserException {
        this.jsonBase64 = jsonBase64;
        this.objectMapper = objectMapper;
        this.classType = classType;
        initializeComponent();
    }

    private void initializeComponent() throws ObjectHashParserException {
        try {
            jsonString = new String( Base64.getDecoder().decode( jsonBase64 ) );
            objInstance = objectMapper.readValue( jsonString, classType );
        } catch ( NullPointerException | JacksonException exc  ) {
            throw new ObjectHashParserException( exc );
        }
    }

    @Override
    public T getInstance() {
        return objInstance;
    }

    @Override
    public byte[] hash( String algorithm ) throws ObjectHashParserException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            return messageDigest.digest( jsonString.getBytes( StandardCharsets.UTF_8 ) );
        } catch ( GeneralSecurityException exc ) {
            throw new ObjectHashParserException( exc );
        }
    }
}
