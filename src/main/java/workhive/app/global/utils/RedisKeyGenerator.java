package workhive.app.global.utils;

public class RedisKeyGenerator {

    private static final String JWT_PREFIX = "jwt:";

    public static String getJwtKey(String userId) {
        return JWT_PREFIX + userId;
    }
}
