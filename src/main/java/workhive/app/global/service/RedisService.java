package workhive.app.global.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import workhive.app.global.handler.RedisHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisHandler redisHandler;

    public void saveValue(String ket, Object value) {
        redisHandler.execute(() -> {
            redisHandler.getValueOperations().set(ket, value);
        });
    }

    public void saveValue(String key, Object value, long timeout) {
        redisHandler.execute(() -> {
            redisHandler.getValueOperations().set(key, value);
            redisHandler.setExpire(key, timeout);
        });
    }

    public Object getValue(String key) {
        return redisHandler.getValueOperations().get(key);
    }

    public void saveHashMap(String key, Map<String, Object> map) {
        redisHandler.execute(() -> {
            redisHandler.getHashOperations().putAll(key, map);
        });
    }

    public void saveHashMap(String key, Map<String, Object> map, long timeout) {
        redisHandler.execute(() -> {
            redisHandler.getHashOperations().putAll(key, map);
            redisHandler.setExpire(key, timeout);
        });
    }

    public Map<String, Object> getHashMap(String key) {
        return redisHandler.getHashOperations().entries(key).entrySet()
                .stream()
                .map(entry -> {
                    String field = (String) entry.getKey();
                    Object value = entry.getValue();
                    return Map.entry(field, value);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void saveList(String key, List<?> list) {
        redisHandler.execute(() -> {
            for (Object o : list) {
                redisHandler.getListOperations().rightPush(key, o);
            }
        });
    }

    public void saveList(String key, List<?> list, long timeout) {
        redisHandler.execute(() -> {
            for (Object o : list) {
                redisHandler.getListOperations().rightPush(key, o);
            }
            redisHandler.setExpire(key, timeout);
        });
    }

    public List<?> getList(String key) {
        return redisHandler.getListOperations().range(key, 0, -1);
    }

    public void remove(String key) {
        redisHandler.execute(() -> {
            redisHandler.delete(key);
        });
    }
}
