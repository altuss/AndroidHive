package com.irvin.rss_reader;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * When user enters a website url this class will do following tasks

 -> Will get the html source code of the website
 -> Parse the html source code and will get rss url
 -> After getting rss url will get rss xml and parse the xml.
 -> Once rss xml parsing is done will return RSSFeed object of the rss xml.
 */

public class RSSParser {

    // RSS XML document CHANNEL tag
    private static String TAG_CHANNEL = "channel";
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_DESCRIPTION = "description";
    private static String TAG_LANGUAGE = "language";
    private static String TAG_ITEM = "item";
    private static String TAG_PUB_DATE = "pubDate";
    private static String TAG_GUID = "guid";

    XMLParser xmlParser;

    public RSSParser() {
    }

    /***
     * Get rss feed from url
     *
     * @param url - is url of the website
     * @return RSSFeed class object
     */
    public RSSFeed getRSSFeed(String url) {
        RSSFeed  rssFeed = null;
        String rssFeedXml = null;
        xmlParser = new XMLParser();

        // 1. Getting rss link from html source code
        String rss_url = this.getRSSLinkFromUrl(url);

        // check if rss_url is founded or not
        if (rss_url != null) {
            // RSS url founded

            // 2. get RSS XML from rss url
            rssFeedXml = xmlParser.getXmlFromUrl(rss_url);

            if (rssFeedXml != null) {
                // successfully fetched rss xml
                // parse the xml
                try {
                    org.w3c.dom.Document document = xmlParser.getDomElement(rssFeedXml);
                    NodeList nodeList = document.getElementsByTagName(TAG_CHANNEL);
                    Element element = (Element) nodeList.item(0);

                    // RSS nodes
                    String title = xmlParser.getValue(element, TAG_TITLE);
                    String link = xmlParser.getValue(element, TAG_LINK);
                    String description = xmlParser.getValue(element, TAG_DESCRIPTION);
                    String language = xmlParser.getValue(element, TAG_LANGUAGE);

                    rssFeed = new RSSFeed(title, description, link, rss_url, language);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // failed to fetch rss xml
            }
        } else {
            // no RSS url found
        }
        return rssFeed;
    }

    /**
     * Getting RSS feed items <item>
     *
     * @param - rss link url of the website
     * @return - List of RSSItem class objects
     * */

    public List<RSSItem> getRSSFeedItems(String rss_url) {
        List<RSSItem> itemsList = new ArrayList<RSSItem>();
        String rssFeedXml;

        // get RSS XML from rss url
        rssFeedXml = xmlParser.getXmlFromUrl(rss_url);

        if (rssFeedXml != null) {
            // successfully fetched rss xml
            // parse the xml

            try {
                org.w3c.dom.Document document = xmlParser.getDomElement(rssFeedXml);
                NodeList nodeList = document.getElementsByTagName(TAG_CHANNEL);
                Element element = (Element) nodeList.item(0);

                // Getting items array
                NodeList items = element.getElementsByTagName(TAG_ITEM);

                // looping through each item
                for (int i = 0; i < items.getLength(); i++) {
                    Element element1 = (Element) items.item(i);

                    String title = xmlParser.getValue(element1, TAG_TITLE);
                    String link = xmlParser.getValue(element1, TAG_LINK);
                    String description = xmlParser.getValue(element1, TAG_DESCRIPTION);
                    String pubdate = xmlParser.getValue(element1, TAG_PUB_DATE);
                    String guid = xmlParser.getValue(element1, TAG_GUID);

                    RSSItem rssItem = new RSSItem(title, link, description, pubdate, guid);

                    // adding items to the list
                    itemsList.add(rssItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return itemsList;
    }

    /**
     * Getting RSS feed link from HTML source code
     *
     * @param ulr is url of the website
     * @returns url of rss link of website
     * */
    private String getRSSLinkFromUrl(String url) {
        // RSS url
        String rss_url = null;

        try {
            // Using JSOUP to parse html source code
            Document doc = Jsoup.connect(url).get();
            // Finding rss links which are having link [type=application/rss+xml]
            Elements links = doc.select("link[type=application/rss+xml]");
            Log.d("No of RSS links found", " " + links.size());

            // check if urls found or not
            if (links.size() > 0) {
                rss_url = links.get(0).attr("href").toString();
            } else {
                // finding rss links which are having link[type=application/rss+xml]
                Elements links1 = doc.select("link[type=application/atom+xml");
                if (links1.size() > 0) {
                    rss_url = links1.get(0).attr("href").toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
