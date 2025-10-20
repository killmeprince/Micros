package mentorship.roadmap.microservices.service_b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class ServiceBApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceBApplication.class, args);
    }

    @Bean
    StringRedisTemplate redisTemplate(RedisConnectionFactory f) {
        return new StringRedisTemplate(f);
    }

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }
}