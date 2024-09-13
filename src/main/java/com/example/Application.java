package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        Integer port = run.getEnvironment().getProperty("server.port", Integer.class);
        printServerInfo(port);
    }

    private static void printServerInfo(Integer port) {
        System.out.printf("""
                --------------------------------------------------
                | Server URL: http://localhost:%d              |
                | Swagger URL: http://localhost:%d/swagger-ui  |
                --------------------------------------------------
                %n""", port, port);
    }

}
