package com.epam.xstack.actuators.prometheuses;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class UserSessionMetrics {
    private final Counter activeSessions;

    public UserSessionMetrics(MeterRegistry registry) {
        activeSessions = registry.counter("user.sessions.active");
    }

    public void incrementActiveSessions() {
        activeSessions.increment();
    }

    public void decrementActiveSessions() {
        activeSessions.increment(-1);
    }
}