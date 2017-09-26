package com.griddynamics.gridquiz;

import com.griddynamics.gridquiz.common.GenerateDateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GridQuizApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GridQuizApplication.class, args);

        //init env

        context.getBean(GenerateDateService.class).initAdmin();
        context.getBean(GenerateDateService.class).generate();
    }
}
