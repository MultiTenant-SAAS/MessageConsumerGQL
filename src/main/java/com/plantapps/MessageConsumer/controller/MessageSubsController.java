package com.plantapps.MessageConsumer.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.plantapps.MessageConsumer.model.Message;

import reactor.core.publisher.Flux;

@Controller
public class MessageSubsController
{
	@Autowired
	private MessagePublisher messagePublisher;
	
	@SubscriptionMapping
	public Flux<Message> messageSubs(@Argument String tenantId) {
        return messagePublisher.getFluxForTenant(tenantId);
    }
	
//	@SubscriptionMapping
//    public Flux<String> subsTest() {
//    	System.out.println("subscription started.....");
//        return Flux.interval(Duration.ofSeconds(1)).map(l->String.valueOf(l));
//    }
	
}
