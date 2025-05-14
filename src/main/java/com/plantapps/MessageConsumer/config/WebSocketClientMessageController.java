package com.plantapps.MessageConsumer.config;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.plantapps.MessageConsumer.model.Message;

@Controller
public class WebSocketClientMessageController
{
//	@MessageMapping("/sendFromTenant")
//	//@SendTo("/topic/site_1")
//	public void receivedMessagetenant1(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) 
//	{
//		System.out.println("====> Message From "+message.getId()+" : "+message.getData());
//	}
	
}
