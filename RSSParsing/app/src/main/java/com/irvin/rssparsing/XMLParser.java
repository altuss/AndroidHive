package com.irvin.rssparsing;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by root on 3/24/15.
 */
public class XMLParser {

    public volatile boolean parsingComplete = true;

    public XMLParser() {
    }

    public RSSFeed ParseXMLAndStore(InputStream stream){
        XmlPullParserFactory xmlPullParserFactory;
        XmlPullParser xmlPullParser = null;
        RSSFeed feed = null;
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            xmlPullParser.setInput(stream, null);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        int event;
        String text = null;

        try {
            event = xmlPullParser.getEventType();
            feed = new RSSFeed();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name  = xmlPullParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = xmlPullParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("title")) {
                            feed.setTitle(text);
                        }
                        else if (name.equals("link")) {
                            feed.setLink(text);
                        }
                        else if (name.equals("description")) {
                            feed.setDescription(text);
                        }
                        else break;
                }
                event = xmlPullParser.next();
            }
            parsingComplete = true;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feed;

    }
    /**
     * Getting RSS feed link from HTML source code
     *
     * @param ulr is url of the website
     * @returns url of rss link of website
     * */
    public String getRSSLinkFromUrl(String url) {
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

    public InputStream fetchXML(String urlAddress) {
        InputStream stream = null;
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            // Starts the query
            stream = connection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
}
