package mtg.cardCatalogue.domain;

import org.springframework.data.annotation.Id;

/**
 * Created by jbo on 31.05.2016.
 */
public class Edition {

    // edition properties
    @Id
    private String set_id;
    private String set;
    private String set_url;


    // card properties
    private String watermark;
    private String rarity;
    private String artist;
    private String multiverse_id;
    private String number;
    private String layout;
    private Price price;
    private String url;
    private String image_url;
    private String store_url;
    private String html_url;

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSet_id() {
        return set_id;
    }

    public void setSet_id(String set_id) {
        this.set_id = set_id;
    }

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMultiverse_id() {
        return multiverse_id;
    }

    public void setMultiverse_id(String multiverse_id) {
        this.multiverse_id = multiverse_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSet_url() {
        return set_url;
    }

    public void setSet_url(String set_url) {
        this.set_url = set_url;
    }

    public String getStore_url() {
        return store_url;
    }

    public void setStore_url(String store_url) {
        this.store_url = store_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    @Override
    public String toString() {
        return String.format("Edition[id=%s, name=%s, price=%s]", set_id, set, price);
    }
}
