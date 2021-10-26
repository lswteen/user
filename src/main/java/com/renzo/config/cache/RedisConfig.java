package com.renzo.config.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @EnableCaching :@Cacheable, @CacheEvict 등의 캐시 어노테이션 활성화를 위한 어노테이션이다. AOP 를 이용한 캐싱 기능을 추상화 시켜주므로
 * 별도의 캐시 관련 로직없이 간단하게 적용해 줄 수 있다. 프록시는 Spring AOP 를 이용하여 동작하며 mode 설정을 통해 aspectj 를 이용한 방법으로 설정해 줄
 * 수도 있다.
 * @Cacheable AOP 를 이용해 메소드가 실행되는 시점에 캐시 존재 여부를 검사하고 캐시가 등록되어 있다면 캐시를 등록하고, 캐시가 등록되어 있다면 메소드를 실행시키지
 * 않고 캐싱된 데이터를 return 해준다. value 설정을 통해 캐시명을 지정해 줄 수 있으며, 같은 캐시명을 사용할 때 key 설정을 통해 캐시에 사용될 인자를 선택할 수
 * 있다.
 * @CacheEvict 메소드 실행시 사용하지 않거나 오래된 캐시의 데이터를 삭제할 수 있다. 메소드의 작업 내용으로 인해 영향을 받는 캐시를 지정해서 사용한다. 기본적으로
 * 메소드가 성공적으로 완료되었을 때 캐시를 삭제하지만 beforeInvocation 설정으로 메소드 실행 전 캐시를 삭제할 수도 있다.
 */

@RequiredArgsConstructor
@EnableCaching
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    /*
     * 자바에서 레디스를 사용하기 위해서 레디스 클라이언트가 필요하다.
     * Spring Data Redis에서는 Jedis와 Lecttuce를 지원한다.
     * netty 위에서 구축되어 비동기로 요청을 처리하므로 성능에 장점이 있는 Lecttuce를 채택하였다.
     * 또한 Document가 잘 만들어져 있고, 디자인 코드 또한 깔끔하다.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisHost,
                redisPort);
        return lettuceConnectionFactory;
    }

    /*
     * RedisTemplate는 커넥션 위에서 레디스 커맨드를 도와준다.
     * 레디스의 데이터 저장방식은 byte[]이기 때문에 값을 저장하고 가져오기 위해서는 직렬화가 필요하다.
     * RedisTemplate 클래스는 default Serializer가 JdkSerializationRedisSerializer이기 때문에
     * 문자열 저장의 특화된 RedisTemplate의 서브 클래스 StringRedisTemplate를 사용했다.
     * StringRedisTemplate의 default Serializer는 StringSerializer이다.
     */

    @Bean(name = "redisTemplate")
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
        return stringRedisTemplate;
    }

}
