package workhive.app.global.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import workhive.app.exception.GeneralException;
import workhive.app.exception.enums.ErrorCode;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (RuntimeException e) {
            log.error("[RedisHandler.set] Error setting value in Redis: {}", e.getMessage());
            throw new GeneralException(ErrorCode.REDIS_VALUE_SET_ERROR, "Error setting value in Redis", e);
        }
    }

    public void set(String key, Object value, long timeout) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout);
        } catch (RuntimeException e) {
            log.error("[RedisHandler.set] Error setting value in Redis with timeout: {}", e.getMessage());
            throw new GeneralException(ErrorCode.REDIS_VALUE_SET_ERROR, "Error setting value in Redis with timeout", e);
        }
    }

    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (RuntimeException e) {
            log.error("[RedisHandler.get] Error getting value from Redis: {}", e.getMessage());
            throw new GeneralException(ErrorCode.REDIS_VALUE_GET_ERROR, "Error getting value from Redis", e);
        }
    }


    public void delete(String key) {
        try {
            if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
                redisTemplate.delete(key);
            }
        } catch (RuntimeException e) {
            log.error("[RedisHandler.delete] Error deleting value from Redis: {}", e.getMessage());
            throw new GeneralException(ErrorCode.REDIS_VALUE_SET_ERROR, "Error deleting value from Redis", e);
        }
    }
}
