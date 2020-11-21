package by.ereut.messagetranslator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("dev")
@PropertySource("classpath:application-dev.properties")
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue}")
    private String queueName;

    @Bean
    public Queue myQueue() {
        return new Queue(queueName);
    }
}
