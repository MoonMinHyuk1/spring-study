package spring.coupon.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    /**
     * Kafka란?
     * 분산 이벤트 스트리밍 플랫폼
     * 이벤트 스트리밍이란 소스에서 목적지까지 이벤트를 실시간으로 스트리밍 하는 것
     *
     * 토픽생성
     * docker exec -it kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic testTopic
     *
     * 프로듀서 실행
     * docker exec -it kafka kafka-console-producer.sh --topic testTopic --broker-list 0.0.0.0:9092
     *
     * 컨슈머 실행
     * docker exec -it kafka kafka-console-consumer.sh --topic testTopic --bootstrap-server localhost:9092
     */

    @Bean
    public ProducerFactory<String, Long> producerFactory() {    //producer 인스턴스를 생성하는데 필요한 설정 값
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  //서버 정보
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); //key serializer 정보
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class); //value serializer 정보

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Long> kafkaTemplate() {    //topic에 데이터를 전송하기 위해 사용할 템플릿
        return new KafkaTemplate<>(producerFactory());
    }
}
