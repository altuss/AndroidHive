package com.irvin.rss_reader;

/**
 * This class file used while inserting data or retrieving data from
 * SQLite database
 **/

 public class WebSite {
    private int id;
    private String title, link, rssLink, description;

    public WebSite() {
    }

    public WebSite(String title, String link, String rssLink, String description) {
        this.title = title;
        this.link = link;
        this.rssLink = rssLink;
        this.description = description;
    }

    /**
     * All set and get methods
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
