package pixelart.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    /**
     * The main entry point for the Spring Boot application.
     *
     * @param args Command-line arguments passed to the application
     */
    public static void main(final String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        System.out.println("Hello pixelart!");
    }
}
