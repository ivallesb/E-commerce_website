package com.learn.mycart.servlets;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet that exposes Prometheus metrics endpoint
 */
public class MetricsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static PrometheusMeterRegistry prometheusMeterRegistry;
    
    // Application-specific metrics
    public static Counter userRegistrations;
    public static Counter userLogins;
    public static Counter productViews;
    public static Counter cartAdditions;
    public static Counter orders;
    public static Timer requestTimer;
    
    @Override
    public void init() throws ServletException {
        super.init();
        initializeMetrics();
    }
    
    /**
     * Initialize Prometheus metrics registry and custom metrics
     */
    private void initializeMetrics() {
        // Create Prometheus registry
        prometheusMeterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        
        // Add the registry to the global registry
        Metrics.addRegistry(prometheusMeterRegistry);
        
        // Initialize custom metrics
        userRegistrations = Counter.builder("mycart_user_registrations_total")
                .description("Total number of user registrations")
                .register(prometheusMeterRegistry);
                
        userLogins = Counter.builder("mycart_user_logins_total")
                .description("Total number of user logins")
                .register(prometheusMeterRegistry);
                
        productViews = Counter.builder("mycart_product_views_total")
                .description("Total number of product views")
                .register(prometheusMeterRegistry);
                
        cartAdditions = Counter.builder("mycart_cart_additions_total")
                .description("Total number of items added to cart")
                .register(prometheusMeterRegistry);
                
        orders = Counter.builder("mycart_orders_total")
                .description("Total number of orders placed")
                .register(prometheusMeterRegistry);
                
        requestTimer = Timer.builder("mycart_request_duration_seconds")
                .description("Request processing time")
                .register(prometheusMeterRegistry);
        
        System.out.println("Metrics initialized successfully");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/plain; version=0.0.4; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            if (prometheusMeterRegistry != null) {
                // Return Prometheus formatted metrics
                String metrics = prometheusMeterRegistry.scrape();
                response.getWriter().write(metrics);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                response.getWriter().write("# Metrics registry not initialized");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("# Error generating metrics: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get the Prometheus registry instance
     */
    public static PrometheusMeterRegistry getRegistry() {
        return prometheusMeterRegistry;
    }
}
