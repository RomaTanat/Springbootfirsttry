package org.example.helloworld;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class HelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(HelloWorldApplication.class);
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.run(args);
    }

}
