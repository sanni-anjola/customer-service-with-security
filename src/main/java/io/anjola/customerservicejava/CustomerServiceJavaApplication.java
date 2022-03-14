package io.anjola.customerservicejava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CustomerServiceJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceJavaApplication.class, args);
    }

}
