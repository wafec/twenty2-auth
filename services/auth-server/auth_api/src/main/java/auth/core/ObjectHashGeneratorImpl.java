package auth.core;

import auth.exceptions.ObjectHashGeneratorException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Base64;

public class ObjectHashGeneratorImpl implements ObjectHashGenerator {
    private final Object objInstance;
    private final ObjectMapper objectMapper;
    private final String algorithm;
    private String jsonObject;
    private String jsonBase64;
    private byte[] jsonHash;

    public ObjectHashGeneratorImpl( Object objInstance, ObjectMapper objectMapper, String algorithm )
            throws ObjectHashGeneratorException {
        this.objInstance = objInstance;
        this.objectMapper = objectMapper;
        this.algorithm = algorithm;
        initializeComponent();
    }

    private void initializeComponent() throws ObjectHashGeneratorException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance( algorithm );
            jsonObject = objectMapper.writeValueAsString( objInstance );
            byte[] jsonObjectBytes = jsonObject.getBytes( StandardCharsets.UTF_8 );
            jsonBase64 = Base64.getEncoder().encodeToString( jsonObjectBytes );
            jsonHash = messageDigest.digest( jsonObjectBytes );
        } catch ( JacksonException | GeneralSecurityException exc ) {
            throw new ObjectHashGeneratorException( exc );
        }
    }

    @Override
    public String jsonObject() {
        return jsonObject;
    }

    @Override
    public String jsonBase64() {
        return jsonBase64;
    }

    @Override
    public byte[] hash() {
        return jsonHash;
    }
}
