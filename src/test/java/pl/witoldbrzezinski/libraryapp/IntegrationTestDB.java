package pl.witoldbrzezinski.libraryapp;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationTestDB {

    @Container
    public static PostgreSQLContainer<?> postgresSQLContainer =
            new PostgreSQLContainer<>("postgres:15.1")
                    .withDatabaseName("libraryapp_test")
                    .withUsername("libraryapp_test")
                    .withPassword("libraryapp_test");

    static {
        postgresSQLContainer.start();
    }
    @DynamicPropertySource
    public static void containerConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgresSQLContainer::getPassword);
    }
}
