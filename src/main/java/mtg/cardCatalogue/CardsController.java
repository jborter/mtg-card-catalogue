package mtg.cardCatalogue;

import mtg.cardCatalogue.domain.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by jbo on 19.04.2016.
 */
@Controller
public class CardsController {

    private static final String CARD_TEMPLATE = "cards";
    private static final String DETAILS_TEMPLATE = "details";
    private static final String[] SORT_CARDS_BY = new String[] {"name"};
    private static final int OFFER_PAGES = 10;

    @Autowired
    private CardRepository cardRepository;

    @Value("${mtg.imageHost}")
    private String imageHost;

    @RequestMapping("/mtg")
    public String getCards(@RequestParam(value="page", required=false, defaultValue="0") int page,
                           @RequestParam(value="size", required=false, defaultValue="20") int size,
                           Model model) {
        Page<Card> cardsPage = cardRepository.findAll(new PageRequest(page, size, Sort.Direction.ASC, SORT_CARDS_BY));
        model.addAttribute("cards", cardsPage.getContent());
        model.addAttribute("activePage", page);
        model.addAttribute("pages", createPages(cardsPage));
        model.addAttribute("imageHost", imageHost);
        return CARD_TEMPLATE;
    }

    @RequestMapping("/mtg/{cardId}")
    public String getCards(@PathVariable("cardId") String cardId, Model model) {
        Card card = cardRepository.findOne(cardId);
        model.addAttribute("card", card);
        model.addAttribute("imageHost", imageHost);
        return DETAILS_TEMPLATE;
    }

    private MtgPage[] createPages(Page page) {
        List<MtgPage> mtgPages = new ArrayList<>();

        int firstPage = Math.max(0, page.getNumber() - OFFER_PAGES);
        int lastPage = Math.min(page.getNumber() + OFFER_PAGES, page.getTotalPages() - 1);
        return IntStream
                .range(firstPage, lastPage)
                .mapToObj( pageNumber -> new MtgPage(pageNumber, page.getSize()))
                .toArray(MtgPage[]::new);
    }

    class MtgPage {
        private String href;
        private int number;

        public MtgPage(int pageNumber, int pageSize) {
            this.href = "?page=" + pageNumber + "&size=" + pageSize;
            this.number = pageNumber;
        }

        public String getHref() {
            return href;
        }

        public int getNumber() {
            return number;
        }
    }

}
