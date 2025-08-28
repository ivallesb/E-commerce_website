package com.learn.mycart.helper;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryProvider {
    private static SessionFactory factory;

    public static SessionFactory getFactory() {
        if (factory == null) {
            try {
                factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to create SessionFactory", e);
            }
        }
        return factory;
    }
}
