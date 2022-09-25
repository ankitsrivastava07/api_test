package test.service.redis_service;
public interface RedisService {
    boolean isPreviousRequestExist(String key);
    void save(String key);
    void remove(String token);

    String get(String Key);
}
