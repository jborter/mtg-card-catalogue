package mtg.cardCatalogue;

import mtg.cardCatalogue.domain.Card;
import mtg.cardCatalogue.domain.Edition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by jbo on 20.06.2016.
 */
@Service
public class CardRepositoryInitializer {

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Value("${mtg.cardInitializer.sourceUrl}")
    private String sourceUrl;

    @Value("${mtg.cardInitializer.absoluteProjectPath}")
    private String absoluteProjectPath;

    @Value("${mtg.cardInitializer.projectImageDir}")
    private String projectImageDir;

    @Value("${mtg.cardInitializer.webappImageDir}")
    private String webappImageDir;

    public void bootstrapRepository() throws IOException {
        cardRepository.deleteAll();
        emptyImgDir();
        loadAllCardsFromDeckbrew();
    }

    private void emptyImgDir() throws IOException {
        Path imgDir = Paths.get(absoluteProjectPath + projectImageDir + webappImageDir);
        if (Files.exists(imgDir)) {
            Files.walk(imgDir).forEach((path) -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                }
            });
        }

        Files.createDirectory(imgDir);
    }

    private void loadAllCardsFromDeckbrew() {
        IntStream
                .range(0, 161)
                .parallel()
                .forEach(page -> loadCardsOfPage(page));

        Logger.getGlobal().log(Level.INFO, "cardRepository filled");
        Logger.getGlobal().log(Level.INFO, "editionRepository filled");
    }

    private void loadCardsOfPage(int page) {
        RestTemplate restTemplate = new RestTemplate();
        Card[] cards = restTemplate.getForObject(sourceUrl + "?page=" + page, Card[].class);
        storeImages(cards);
        cardRepository.save(Arrays.asList(cards));
        extractAndStoreEditions(cards);
    }
    private void storeImages(Card[] cards) {
        RestTemplate restTemplate = new RestTemplate();
        Stream
                .of(cards)
                .flatMap(card -> Stream.of(card.getEditions()))
                .parallel()
                .forEach(edition -> {
                    String originalImageUrl = edition.getImage_url();
                    byte[] imageBytes = restTemplate.getForObject(originalImageUrl, byte[].class);
                    try {
                        String newImageUrl = webappImageDir + "/" + UUID.randomUUID() + originalImageUrl.substring(originalImageUrl.lastIndexOf("."));
                        Path imagePath = Paths.get(absoluteProjectPath + projectImageDir + newImageUrl);
                        Files.write(imagePath, imageBytes);
                        Logger.getGlobal().info("Image stored: " + imagePath.toString());
                        edition.setImage_url(newImageUrl);
                    } catch (IOException e) {
                        Logger.getGlobal().log(Level.WARNING, "Could not store image " + originalImageUrl, e);
                    }
                });
    }

    private void extractAndStoreEditions(Card[] cards) {
        Edition[] editions = Stream
                .of(cards)
                .flatMap(card -> Stream.of(card.getEditions()))
                .toArray(Edition[]::new);
        editionRepository.save(Arrays.asList(editions));
    }
}
