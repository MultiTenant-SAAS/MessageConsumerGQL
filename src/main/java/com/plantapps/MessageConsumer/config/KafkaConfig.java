package com.plantapps.MessageConsumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.plantapps.MessageConsumer.kafka.MessageDeserializer;
import com.plantapps.MessageConsumer.model.Message;

//@Configuration
public class KafkaConfig
{
	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootrapServer;
	
	@Bean
	public Map<String, Object> consumerConfig()
	{
		Map<String, Object> map = new HashMap<>();
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootrapServer);
		map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageDeserializer.class);
		return map;
	}
	
	@Bean
	public ConsumerFactory<String, Message> consumerFactory()
	{
		return new DefaultKafkaConsumerFactory<>(consumerConfig());
	}
	
}
