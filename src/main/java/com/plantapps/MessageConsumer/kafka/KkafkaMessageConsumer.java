package com.plantapps.MessageConsumer.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.plantapps.MessageConsumer.controller.MessagePublisher;
import com.plantapps.MessageConsumer.model.Message;

@Service
public class KkafkaMessageConsumer
{
//	@Autowired
//	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private MessagePublisher messagePublisher;
	
	@KafkaListener(topics = "site_1", groupId = "message_1")
	public void mesageConsumer1(ConsumerRecord<String, Message> record)
	{
		try
		{
		Message message = record.value();
		//String id = new String(record.headers().lastHeader("tenantId").value());
		System.out.println("the message received from site_1 tenant-1 is : " + message.getData());
		//messagingTemplate.convertAndSend("/topic/site_1", message);
		messagePublisher.publish("tenant-"+message.getId(), message);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@KafkaListener(topics = "site_2", groupId = "message_1")
	public void mesageConsumer2(ConsumerRecord<String, Message> record)
	{
		try
		{
		Message message = record.value();
		//String id = new String(record.headers().lastHeader("tenantId").value());

		System.out.println("the message received from site_2 tenant-2 is : " + message.getData());
		//messagingTemplate.convertAndSend("/topic/site_2", message);
		messagePublisher.publish("tenant-"+message.getId(), message);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
