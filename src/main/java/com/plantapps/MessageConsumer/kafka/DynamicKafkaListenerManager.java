package com.plantapps.MessageConsumer.kafka;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import com.plantapps.MessageConsumer.model.Message;

//@Component
public class DynamicKafkaListenerManager
{
	private final KafkaListenerEndpointRegistry registry;
    //private final Map<String, Object> defaultConsumerProps;

    private final Map<String, MessageListenerContainer> tenantContainers = new ConcurrentHashMap<>();
    
    @Autowired
    private ConsumerFactory<String, Message> consumerFactory;
    
    public DynamicKafkaListenerManager(KafkaListenerEndpointRegistry registry /*,
                                        @Qualifier("defaultConsumerProps") Map<String, Object> defaultConsumerProps */) {
        this.registry = registry;
//        this.defaultConsumerProps = defaultConsumerProps;
//        defaultConsumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        defaultConsumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        defaultConsumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageDeserializer.class);
    }
    
    public void registerTenantListener(String siteName) {
    	String topic = siteName; // e.g., site_1 or site_2
        String listenerId = "listener-" + siteName;
        String groupId = "message_"+siteName;

//        if (registry.getListenerContainer(listenerId) != null) {
//            return; // already registered
//        }
        if (tenantContainers.containsKey(listenerId)) {
            return; // already registered
        }

//        Map<String, Object> tenantProps = new HashMap<>(defaultConsumerProps);
//        tenantProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        ConsumerFactory<String, Message> consumerFactory = new DefaultKafkaConsumerFactory<>(tenantProps);

        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        ConcurrentMessageListenerContainer<String, Message> container = factory.createContainer(topic);
        container.getContainerProperties().setMessageListener((MessageListener<String, Message>) record -> {
            Message msg = record.value();
            System.out.printf("Site [%s] received message: id=%d, data=%s%n", siteName, msg.getId(), msg.getData());
        });

        container.setBeanName(listenerId);
        container.start();
        tenantContainers.put(listenerId, container); // keep track if needed
        System.out.println("Listener started for site: " + siteName);
    }
    
    public void stopTenantListener(String tenantId) {
        String listenerId = "listener-" + tenantId;
        MessageListenerContainer container = tenantContainers.remove(listenerId);
        if (container != null) {
            container.stop();
        }
    }
}
