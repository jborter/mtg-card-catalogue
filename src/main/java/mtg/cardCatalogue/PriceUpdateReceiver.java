package mtg.cardCatalogue;

import com.fasterxml.jackson.databind.ObjectMapper;
import mtg.cardCatalogue.domain.Card;
import mtg.cardCatalogue.domain.Edition;
import mtg.cardCatalogue.domain.PriceUpdate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PriceUpdateReceiver {

    private final static String TOPIC = "topic-mtg-price-updates";
    private final static String QUEUE = "queue-mtg-price-update-mtg-card-catalogue";
    private final static String ROUTING_KEY = "card.price.update";

    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    private CardRepository cardRepository;

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    Queue queue() {
        return new Queue(QUEUE, false);
    }

    @Bean
    PriceUpdateReceiver receiver() {
        return new PriceUpdateReceiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(PriceUpdateReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public void receiveMessage(String jsonString) {
        Logger.getGlobal().info("Received JSON object: " + jsonString);
        try {
            PriceUpdate priceUpdate = getPriceUpdate(jsonString);
            updateCard(priceUpdate);
        } catch (IOException e) {
            Logger.getGlobal().log(Level.WARNING, "Could not process price update: " + e);
        }
    }

    private void updateCard(PriceUpdate priceUpdate) {
        Card affectedCard = cardRepository.findOne(priceUpdate.getCardId());
        updatePrice(affectedCard.getEditions(), priceUpdate);
        cardRepository.save(affectedCard);
    }

    private void updatePrice(Edition[] editions, PriceUpdate priceUpdate) {
        for (Edition e : editions) {
            if (e.getSet_id().equals(priceUpdate.getEditionId())){
                e.setPrice(priceUpdate.getPrice());
            }
        }
    }

    private PriceUpdate getPriceUpdate(String jsonString) throws java.io.IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, PriceUpdate.class);
    }

    @PreDestroy
    public void cleanUp() {
        context.close();
    }
}
