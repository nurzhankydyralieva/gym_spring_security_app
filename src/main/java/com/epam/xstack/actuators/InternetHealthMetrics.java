package com.epam.xstack.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.URLConnection;

@Component
public class InternetHealthMetrics implements HealthIndicator {
    @Override
    public Health health() {
        return checkInternetConnection() == true ?
                Health.up().withDetail("Success code", "Active Internet Connection").build() :
                Health.down().withDetail("Error code", "Inactive Internet Connection").build();

    }

    private boolean checkInternetConnection() {
        boolean flag;
        try {
            URL url = new URL("https://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
