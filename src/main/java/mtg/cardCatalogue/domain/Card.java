package mtg.cardCatalogue.domain;

import org.springframework.data.annotation.Id;

/**
 * Created by jbo on 27.04.2016.
 *
 *
 */
public class Card {

    @Id
    private String id;

    private String name;
    private String url;
    private String[] types;
    private String[] subtypes;
    private String[] colors;
    private String cmc;
    private String cost;
    private String text;
    private String power;
    private String toughness;
    private Format formats;
    private Edition[] editions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getTypes() {
        return types == null ? new String[0] : types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String[] getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(String[] subtypes) {
        this.subtypes = subtypes;
    }

    public String[] getColors() {
        return colors == null ? new String[0] : colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public String getCmc() {
        return cmc;
    }

    public void setCmc(String cmc) {
        this.cmc = cmc;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public Format getFormats() {
        return formats;
    }

    public void setFormats(Format formats) {
        this.formats = formats;
    }

    public Edition[] getEditions() {
        return editions;
    }

    public void setEditions(Edition[] editions) {
        this.editions = editions;
    }

    @Override
    public String toString() {
        return String.format("Card[id=%s, name='%s']", id, name);
    }
}

class Format {
    private String commander;
    private String legacy;
    private String modern;
    private String standard;
    private String vintage;

    public String getCommander() {
        return commander;
    }

    public void setCommander(String commander) {
        this.commander = commander;
    }

    public String getLegacy() {
        return legacy;
    }

    public void setLegacy(String legacy) {
        this.legacy = legacy;
    }

    public String getModern() {
        return modern;
    }

    public void setModern(String modern) {
        this.modern = modern;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getVintage() {
        return vintage;
    }

    public void setVintage(String vintage) {
        this.vintage = vintage;
    }
}