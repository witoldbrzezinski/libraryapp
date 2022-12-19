package pl.witoldbrzezinski.libraryapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LibraryappApplicationTests extends IntegrationTestDB {

  @Test
  void contextLoads() {}
}
