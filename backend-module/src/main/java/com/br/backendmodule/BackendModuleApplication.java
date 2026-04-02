package com.br.backendmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.br.backendmodule", "com.br.ejb.service"})
public class BackendModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendModuleApplication.class, args);
    }

}
