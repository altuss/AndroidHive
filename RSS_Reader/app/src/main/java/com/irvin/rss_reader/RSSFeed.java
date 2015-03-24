package com.irvin.rss_reader;

import java.util.List;

/**
 * This class handles rss xml
 */
public class RSSFeed {
    // xml nodes
    private String title, description, link, rssLink, language;
    private List<RSSItem> items;

    public RSSFeed(String title, String description, String link, String rssLink, String language) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.rssLink = rssLink;
        this.language = language;
    }

    /**
     * All set methods
     */
    public void setItems(List<RSSItem> items) {
        this.items = items;
    }

    /**
     * ALl get methods
     */
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getRssLink() {
        return rssLink;
    }

    public String getLanguage() {
        return language;
    }

    public List<RSSItem> getItems() {
        return items;
    }
}
