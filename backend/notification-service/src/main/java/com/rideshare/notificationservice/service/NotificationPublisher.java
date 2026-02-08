package com.rideshare.notificationservice.service;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationPublisher {
    
    // Store active SSE connections by user email
    private final Map<String, SseEmitter> activeConnections = new ConcurrentHashMap<>();
    
    public void addConnection(String userEmail, SseEmitter emitter) {
        activeConnections.put(userEmail, emitter);
        
        emitter.onCompletion(() -> activeConnections.remove(userEmail));
        emitter.onTimeout(() -> activeConnections.remove(userEmail));
        emitter.onError((ex) -> activeConnections.remove(userEmail));
    }
    
    public void sendNotification(String userEmail, String type, String message) {
        SseEmitter emitter = activeConnections.get(userEmail);
        if (emitter != null) {
            try {
                Map<String, String> notificationData = new java.util.HashMap<>();
                notificationData.put("type", type);
                notificationData.put("message", message);
                notificationData.put("timestamp", java.time.LocalDateTime.now().toString());
                
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(notificationData));
            } catch (IOException e) {
                emitter.completeWithError(e);
                activeConnections.remove(userEmail);
            }
        }
    }
    
    public void removeConnection(String userEmail) {
        activeConnections.remove(userEmail);
    }
}

