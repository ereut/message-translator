package by.ereut.messagetranslator.services;

import by.ereut.messagetranslator.custompair.CustomPair;
import by.ereut.messagetranslator.custompair.CustomPairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"test", "dev"})
@PropertySource("classpath:application-dev.properties")
@Slf4j
public class AmqpService {

    private final RabbitTemplate rabbitTemplate;
    private final CustomPairService service;
    @Value("${application.less-balance-value}")
    private Double lessBalanceValue;
    @Value("${spring.rabbitmq.queue}")
    private String routingKey;

    public AmqpService(CustomPairService service, RabbitTemplate rabbitTemplate) {
        this.service = service;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(initialDelayString = "${scheduler.fixed-delay.amqp}",
            fixedDelayString = "${scheduler.fixed-delay.amqp}")
    public void sendMessageToRabbit() {
        List<CustomPair> customPairs = service.findPairWithLessThanBalance(lessBalanceValue);
        rabbitTemplate.convertAndSend(routingKey, customPairs);
        log.info(customPairs.size() + " items was send to " + routingKey + " queue");
    }

}
