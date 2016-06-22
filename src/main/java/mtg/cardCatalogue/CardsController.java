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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by jbo on 19.04.2016.
 */
@Controller
public class CardsController {

    private static final String CARD_TEMPLATE = "cards";
    private static final String DETAILS_TEMPLATE = "details";
    private static final String[] SORT_CARDS_BY = new String[] {"name"};
    private static final int OFFER_PAGES = 9;

    @Autowired
    private CardRepository cardRepository;

    @Value("${mtg.imageHost}")
    private String imageHost;

    @RequestMapping("/mtg")
    public String getCards(@RequestParam(value="page", required=false, defaultValue="0") int page,
                           @RequestParam(value="size", required=false, defaultValue="20") int size,
                           @RequestParam(value="s", required=false, defaultValue="") String searchBy,
                           Model model) {
        Page<Card> cardsPage = loadCardPage(page, size, searchBy);
        model.addAttribute("cards", cardsPage.getContent());
        model.addAttribute("activePage", page);
        model.addAttribute("searchBy", searchBy);
        model.addAttribute("pages", createPages(cardsPage, searchBy));
        model.addAttribute("imageHost", imageHost);
        return CARD_TEMPLATE;
    }

    private Page<Card> loadCardPage(int page, int size, String searchBy) {
        if (searchBy == null || searchBy.trim().isEmpty()) {
            return cardRepository.findAll(new PageRequest(page, size, Sort.Direction.ASC, SORT_CARDS_BY));
        } else {
            return cardRepository.findByNameIgnoreCaseLike(searchBy, new PageRequest(page, size, Sort.Direction.ASC, SORT_CARDS_BY));
        }
    }

    @RequestMapping("/mtg/{cardId}")
    public String getCards(@PathVariable("cardId") String cardId, Model model) {
        Card card = cardRepository.findOne(cardId);
        model.addAttribute("card", card);
        model.addAttribute("imageHost", imageHost);
        return DETAILS_TEMPLATE;
    }

    private List<MtgPage> createPages(Page page, String searchBy) {
        List<MtgPage> mtgPages = new ArrayList<>();

        int firstPage = Math.max(0, page.getNumber() - OFFER_PAGES);
        int lastPage = Math.min(firstPage + (2 * OFFER_PAGES), page.getTotalPages() - 1);
        mtgPages = IntStream
                .range(firstPage, lastPage + 1)
                .mapToObj( pageNumber -> new MtgPage(pageNumber, page.getSize(), searchBy))
                .collect(Collectors.toList());

        if (firstPage > 0) {
            MtgPage mtgPage = new MtgPage(firstPage - 1, page.getSize(), searchBy);
            mtgPage.setJumpLeftPage(true);
            mtgPages.add(0, mtgPage);
        }

        if (lastPage < page.getTotalPages() - 1) {
            MtgPage mtgPage = new MtgPage(lastPage + 1, page.getSize(), searchBy);
            mtgPage.setJumpRightPage(true);
            mtgPages.add(mtgPage);
        }

        return mtgPages;
    }

    class MtgPage {
        private String href;
        private int number;
        private boolean jumpLeftPage = false;
        private boolean jumpRightPage = false;

        public MtgPage(int pageNumber, int pageSize, String searchBy) {
            this.href = "?page=" + pageNumber + "&size=" + pageSize + "&s=" + searchBy;
            this.number = pageNumber;
        }

        public String getHref() {
            return href;
        }

        public int getNumber() {
            return number;
        }

        public boolean isJumpLeftPage() {
            return jumpLeftPage;
        }

        public void setJumpLeftPage(boolean jumpLeftPage) {
            this.jumpLeftPage = jumpLeftPage;
        }

        public boolean isJumpRightPage() {
            return jumpRightPage;
        }

        public void setJumpRightPage(boolean jumpRightPage) {
            this.jumpRightPage = jumpRightPage;
        }
    }

}
