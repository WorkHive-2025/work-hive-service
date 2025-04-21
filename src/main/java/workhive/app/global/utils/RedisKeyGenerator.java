package workhive.app.global.utils;

public class RedisKeyGenerator {

    private static final String JWT_PREFIX = "jwt:";
    private static final String ROLE_PREFIX = "role:";

    public static String getJwtKey(String userId) {
        return JWT_PREFIX + userId;
    }

    public static String getRolePrefix(String code) {
        return ROLE_PREFIX + code + ":permissions";
    }
}
