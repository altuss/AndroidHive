package com.irvin.rss_reader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ProgressDialog pDialog;

    ArrayList<HashMap<String,String>> rssFeedList;
    RSSParser rssParser = new RSSParser();
    RSSFeed rssFeed;

    private ImageButton btnAddSite;

    // array to trace sqlite ids
    String[] sqliteIds;

    public static String TAG_ID = "id";
    public static String TAG_TITLE = "title";
    public static String TAG_LINK = "link";

    // List view
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_list);

        btnAddSite = (ImageButton) findViewById(R.id.btnAddSite);
        // Hashmap for ListView
        rssFeedList = new ArrayList<HashMap<String, String>>();

        new loadStoreSites().execute();

        lv = (ListView) findViewById(R.id.list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting values from selected ListItem
                String sqlite_id = ((TextView) view.findViewById(R.id.sqlite_id)).getText().toString();

                /*Intent intent = new Intent(getApplicationContext(), ListRSSItemsActivity.class);

                intent.putExtra(TAG_ID, sqlite_id);
                startActivity(intent);*/
            }
        });

        /**
         * Add new site on button click
         */
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddNewSiteActivity.class);
                // starting new activity and expecting some response back
                // depending on the result will decide whether new website is
                // added to SQLite database or not
                startActivityForResult(i, 100);
            }
        });
    }

    /**
     * Response from AddNewSiteActivity.java
     * if response is 100 means new site is added to sqlite
     * reload this activity again to show
     * newly added website in listview
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {

            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    /**
     * Building a context menu for listview
     * Long press on List row to see context menu
     * */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.list) {
            menu.setHeaderTitle("Delete");
            menu.add(Menu.NONE, 0, 0, "Delete Feed");
        }
    }

    /**
     * Responding to context menu selected option
     * */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        // check for selected option
        if(menuItemIndex == 0){
            // user selected delete
            // delete the feed
            RSSDatabaseHandler rssDb = new RSSDatabaseHandler(getApplicationContext());
            WebSite site = new WebSite();
            site.setId(Integer.parseInt(sqliteIds[info.position]));
            rssDb.deleteSite(site);
            //reloading same activity again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

        return true;
    }

    /**
     * Background Async Task to get RSS data from URL
     * */
    class loadStoreSites extends AsyncTask<String, String, String> {
        ListAdapter adapter;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading websites ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            RSSDatabaseHandler rssDb = new RSSDatabaseHandler(
                    getApplicationContext());

            // listing all websites from SQLite
            List<WebSite> siteList = rssDb.getAllSites();

            sqliteIds = new String[siteList.size()];

            // loop through each website
            for (int i = 0; i < siteList.size(); i++) {

                WebSite s = siteList.get(i);

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                map.put(TAG_ID, String.valueOf(s.getId()));
                map.put(TAG_TITLE, s.getTitle());
                map.put(TAG_LINK, s.getLink());

                // adding HashList to ArrayList
                rssFeedList.add(map);

                // add sqlite id to array
                // used when deleting a website from sqlite
                sqliteIds[i] = String.valueOf(s.getId());
            }
            /**
             * Updating list view with websites
             * */
            adapter = new SimpleAdapter(
                    MainActivity.this,
                    rssFeedList, R.layout.site_list_row,
                    new String[] { TAG_ID, TAG_TITLE, TAG_LINK },
                    new int[] { R.id.sqlite_id, R.id.title, R.id.link });


            return null;
         }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            lv.setAdapter(adapter);
            registerForContextMenu(lv);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
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
}
