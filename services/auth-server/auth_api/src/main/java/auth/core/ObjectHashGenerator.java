package auth.core;

public interface ObjectHashGenerator {
    String jsonObject();

    String jsonBase64();

    byte[] hash();
}
