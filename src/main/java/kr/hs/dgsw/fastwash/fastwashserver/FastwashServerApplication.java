package kr.hs.dgsw.fastwash.fastwashserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FastwashServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastwashServerApplication.class, args);
    }

}
