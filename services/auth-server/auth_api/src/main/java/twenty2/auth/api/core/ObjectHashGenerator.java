package twenty2.auth.api.core;

public interface ObjectHashGenerator {
    String jsonObject();

    String jsonBase64();

    byte[] hash();
}
