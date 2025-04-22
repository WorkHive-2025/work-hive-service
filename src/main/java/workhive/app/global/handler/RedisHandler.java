package workhive.app.global.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import workhive.app.exception.GeneralException;
import workhive.app.exception.enums.ErrorCode;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 리스트에 접근하여 다양한 연산을 수행합니다.
     *
     * @return ListOperations<String, Object>
     */
    public ListOperations<String, Object> getListOperations() {
        return redisTemplate.opsForList();
    }

    /**
     * 해시에 접근하여 다양한 연산을 수행합니다.
     *
     * @return HashOperations<String, Object, Object>
     */
    public HashOperations<String, Object, Object> getHashOperations() {
        return redisTemplate.opsForHash();
    }

    /**
     * 단일 데이터에 접근하여 다양한 연산을 수행합니다.
     *
     * @return ValueOperations<String, Object>
     */
    public ValueOperations<String, Object> getValueOperations() {
        return redisTemplate.opsForValue();
    }

    public void setExpire(String key, long timeout) {
        if (Boolean.TRUE.equals(hasKey(key))) {
            redisTemplate.expire(key, timeout, TimeUnit.MICROSECONDS);
        }
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void delete(String key) {
        if (Boolean.TRUE.equals(hasKey(key))) {
            redisTemplate.delete(key);
        }
    }

    /**
     * Redis 작업중 등록, 수정, 삭제에 대해서 처리 및 예외처리를 수행합니다.
     *
     * @param operation
     * @return
     */
    public void execute(Runnable operation) {
        try {
            operation.run();
        } catch (Exception e) {
            log.error("[RedisHandler.execute] Error executing Redis operation: {}", e.getMessage());
            throw new GeneralException(ErrorCode.REDIS_VALUE_SET_ERROR, "Error executing Redis operation", e);
        }
    }
}
