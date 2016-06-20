package mtg.cardCatalogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class Application implements CommandLineRunner {

    @Autowired
    private CardRepositoryInitializer cardRepositoryInitializer;

    @Value("${mtg.initializeCardsRepositoryOnStartup}")
    private boolean initializeCardsRepositoryOnStartup;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (initializeCardsRepositoryOnStartup) {
            cardRepositoryInitializer.bootstrapRepository();
        }
    }


}