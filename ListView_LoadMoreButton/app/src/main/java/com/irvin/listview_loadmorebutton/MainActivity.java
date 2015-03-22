package com.irvin.listview_loadmorebutton;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    // All variables
    private XMLParser parser;
    private Document document;
    private String xml;
    private ListView listView;
    private ListViewAdapter adapter;
    private ArrayList<HashMap<String, String>> menuItems;
    private ProgressDialog pDialog;


    // XML node names
    private static final String KEY_ITEM = "item"; // parent node
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    // Flag for current page
    private int current_page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        menuItems = new ArrayList<HashMap<String, String>>();

        new loadMoreListView().execute();

        // LoadMore button
        Button loadMore = new Button(this);
        loadMore.setText("Load more");
        listView.addFooterView(loadMore);

        /**
         * Listening to Load More button click event
         */
        loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start async task
                new loadMoreListView().execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class loadMoreListView extends AsyncTask<Void, Void, Void> {
        int currentPosition;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // increment current page
            current_page++;
            // next page
            String URL = "http://api.androidhive.info/list_paging/?page=" + current_page;
            parser = new XMLParser();

            xml = parser.getXmlFromUrl(URL);
            document = parser.getDomElement(xml);

            NodeList nl = document.getElementsByTagName(KEY_ITEM);
            // looping through all item nodes <item>
            for (int i = 0; i < nl.getLength(); i++) {
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nl.item(i);

                // adding each child node to HashMap key => value
                map.put(KEY_ID, parser.getValue(e, KEY_ID));
                map.put(KEY_NAME, parser.getValue(e, KEY_NAME));

                // adding HashList to ArrayList
                menuItems.add(map);
            }
            // get list View current position - used to maintain scroll position

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
            currentPosition = listView.getFirstVisiblePosition();
            adapter = new ListViewAdapter(MainActivity.this, menuItems);
            listView.setAdapter(adapter);
            listView.setSelectionFromTop(currentPosition + 10, 0);
        }
    }
}
