package com.irvin.rssparsing;

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
