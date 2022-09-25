package test.service.redis_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.service.redis_service.RedisService;
import test.service.redis_service.template.RedisTemplateService;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplateService redisTemplateService;

    @Override
    public boolean isPreviousRequestExist(String key) {
        return redisTemplateService.isKeyExist(key);
    }

    @Override
    public void save(String key) {
        redisTemplateService.save(key);
    }

    @Override
    public void remove(String token) {
        redisTemplateService.remove(token);
    }

    @Override
    public String get(String key) {
        return redisTemplateService.get(key);
    }
}
