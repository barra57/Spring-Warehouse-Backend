package whizware.whizware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class WhizwareApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhizwareApplication.class, args);
    }

}
