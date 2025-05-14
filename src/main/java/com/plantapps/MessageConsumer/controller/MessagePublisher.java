package com.plantapps.MessageConsumer.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.plantapps.MessageConsumer.model.Message;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class MessagePublisher
{
	private final Map<String, Sinks.Many<Message>> tenantSinks = new ConcurrentHashMap<>();

    public Sinks.Many<Message> getSinkForTenant(String tenantId) {
        return tenantSinks.computeIfAbsent(tenantId, k ->
                Sinks.many().multicast().onBackpressureBuffer());
    }

    public void publish(String tenantId, Message message) {
        getSinkForTenant(tenantId).tryEmitNext(message);
    }

    public Flux<Message> getFluxForTenant(String tenantId) {
        return getSinkForTenant(tenantId).asFlux();
    }
}
