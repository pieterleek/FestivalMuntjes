package io.qman.festivalcoins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static io.qman.festivalcoins.util.SecureHasher.secureHash;

@SpringBootApplication
public class FestivalCoinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FestivalCoinsApplication.class, args);
    }

}
