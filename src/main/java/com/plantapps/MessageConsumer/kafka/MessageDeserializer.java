package com.plantapps.MessageConsumer.kafka;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantapps.MessageConsumer.model.Message;

public class MessageDeserializer implements Deserializer<Message>
{
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public Message deserialize(String topic, byte[] data)
	{
		try
		{
			return objectMapper.readValue(data, Message.class);
		}
		catch (Exception e) {
			throw new RuntimeException("deserialization error");
		}
	}

}
