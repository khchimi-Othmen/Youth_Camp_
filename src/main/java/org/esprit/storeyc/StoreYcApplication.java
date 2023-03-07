package org.esprit.storeyc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class StoreYcApplication {


    public static void main(String[] args) {
        SpringApplication.run(StoreYcApplication.class, args);
    }


}
