package test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
@SpringBootApplication
public class ApiTestApplication {
    public static void main(String[] args) {

        SpringApplication.run(ApiTestApplication.class, args);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplateConfig(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
