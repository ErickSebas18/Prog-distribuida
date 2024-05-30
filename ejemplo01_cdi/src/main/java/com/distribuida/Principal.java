package com.distribuida;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Principal {
    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer
                .newInstance().initialize();
    }
}
