package mtg.cardCatalogue.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceUpdate {
    @JsonProperty("card-id")
    private String cardId;
    @JsonProperty("edition-id")
    private String editionId;
    private Price price;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getEditionId() {
        return editionId;
    }

    public void setEditionId(String editionId) {
        this.editionId = editionId;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}

