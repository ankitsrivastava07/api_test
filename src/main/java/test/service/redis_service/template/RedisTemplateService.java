package test.service.redis_service.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Random;

@Component
public class RedisTemplateService {

    public static final String REQUEST_KEY = "REQUEST_KEY";
    public static final String RANDOM_STRING = "" + Math.random();
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init() {
        hashOperations = redisTemplate.opsForHash();
    }
    public boolean isKeyExist(String key) {
        return get(key) != null ? true : false;
    }

    public void save(String key) {
        hashOperations.put(REQUEST_KEY, key, key);
    }

    public String get(String key) {
        return hashOperations.get(REQUEST_KEY, key);
    }
    public void remove(String token) {
        hashOperations.delete(REQUEST_KEY, token);
    }
}
