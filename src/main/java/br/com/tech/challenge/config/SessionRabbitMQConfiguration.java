package br.com.tech.challenge.config;

import br.com.tech.challenge.service.SessionService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionRabbitMQConfiguration {

    public static final String QUEUE_NAME = "session.result.queue";
    public static final String ROUTING_KEY = "session.result";
    public static final String TOPIC_EXCHANGE_NAME = "session.result.exchange";
    public static final String DELAY_HEADER = "x-delay";
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public DirectExchange exchange() {
        var direct = new DirectExchange(TOPIC_EXCHANGE_NAME);
        direct.setDelayed(true);
        return direct;
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             @Qualifier("sessionListener") MessageListener listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listener);
        return container;
    }

    @Bean(name = "sessionListener")
    MessageListener listenerAdapter(SessionService sessionService) {
        return (message) -> {
            sessionService.closeSession(new String(message.getBody()));
        };
    }

}