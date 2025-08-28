package com.learn.mycart.helper;

import com.learn.mycart.servlets.MetricsServlet;
import io.micrometer.core.instrument.Timer;

/**
 * Helper class to instrument application with metrics
 */
public class MetricsHelper {
    
    /**
     * Increment user registration counter
     */
    public static void incrementUserRegistrations() {
        if (MetricsServlet.userRegistrations != null) {
            MetricsServlet.userRegistrations.increment();
        }
    }
    
    /**
     * Increment user login counter
     */
    public static void incrementUserLogins() {
        if (MetricsServlet.userLogins != null) {
            MetricsServlet.userLogins.increment();
        }
    }
    
    /**
     * Increment product views counter
     */
    public static void incrementProductViews() {
        if (MetricsServlet.productViews != null) {
            MetricsServlet.productViews.increment();
        }
    }
    
    /**
     * Increment cart additions counter
     */
    public static void incrementCartAdditions() {
        if (MetricsServlet.cartAdditions != null) {
            MetricsServlet.cartAdditions.increment();
        }
    }
    
    /**
     * Increment orders counter
     */
    public static void incrementOrders() {
        if (MetricsServlet.orders != null) {
            MetricsServlet.orders.increment();
        }
    }
    
    /**
     * Start timing a request
     */
    public static Timer.Sample startTimer() {
        if (MetricsServlet.requestTimer != null) {
            return Timer.start(MetricsServlet.getRegistry());
        }
        return null;
    }
    
    /**
     * Stop timing a request
     */
    public static void stopTimer(Timer.Sample sample, String operation) {
        if (sample != null && MetricsServlet.requestTimer != null) {
            sample.stop(Timer.builder("mycart_request_duration_seconds")
                    .tag("operation", operation)
                    .register(MetricsServlet.getRegistry()));
        }
    }
}
