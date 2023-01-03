package pl.witoldbrzezinski.libraryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableSwagger2
@SpringBootApplication
public class LibraryappApplication {

  public static void main(String[] args) {
    SpringApplication.run(LibraryappApplication.class, args);
  }
}
