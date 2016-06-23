package mtg.cardCatalogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan
public class Application implements CommandLineRunner {

    @Autowired
    private CardRepositoryInitializer cardRepositoryInitializer;

    @Value("${mtg.initializeCardsRepositoryOnStartup}")
    private boolean initializeCardsRepositoryOnStartup;

    public static void main(String[] args) {
        SpringApplication notificationMicroService = new SpringApplication(Application.class);
        notificationMicroService.addListeners(new ApplicationPidFileWriter("notification-micro-service.pid"));
        notificationMicroService.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (initializeCardsRepositoryOnStartup) {
            cardRepositoryInitializer.bootstrapRepository();
        }
    }


}