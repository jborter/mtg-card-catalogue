package mtg.cardCatalogue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PriceUpdateReceiver {

    final static String queueName = "mtg-price-update";

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    PriceUpdateReceiver receiver() {
        return new PriceUpdateReceiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(PriceUpdateReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public void receiveMessage(String message) {
        System.out.println("Receiving message...");
        System.out.println("Received <" + message + ">");
    }
}
