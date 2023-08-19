package com.example.demo.badge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class BadgeConfig {
    @Bean
    CommandLineRunner commandLineRunner(BadgeRepository repository){
        List<String> zone_acces = new ArrayList<>();
        zone_acces.add("green");
        zone_acces.add("blue");
        return args -> {
          Badge badge1 = new Badge("yaden","alaoddine",
                  "anp","cd677873",
                  "cpt","C:\\Users\\alao\\Desktop\\stage\\photos\\1.jpeg",
                  LocalDate.of(2023, Month.JULY,2),
                  LocalDate.of(2023, Month.JULY,2),
                  LocalDate.of(2023, Month.JULY,2),
                  zone_acces,LocalDate.of(2023, Month.JULY,2));
            Badge badge2 = new Badge("yaden","alaoddine",
                    "anp","cd677893",
                    "cpt","C:\\Users\\alao\\Desktop\\stage\\photos\\1.jpeg",
                    LocalDate.of(2023, Month.JULY,2),
                    LocalDate.of(2023, Month.JULY,2),
                    LocalDate.of(2023, Month.JULY,2),
                    zone_acces
                    ,LocalDate.of(2023, Month.JULY,2));
          repository.saveAll(
                  List.of(badge1,badge2)
          );
        };

    }
}
