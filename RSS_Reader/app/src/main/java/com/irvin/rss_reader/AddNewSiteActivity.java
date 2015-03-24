package com.irvin.rss_reader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddNewSiteActivity extends ActionBarActivity {

    Button btnSubmit;
    Button btnCancel;
    EditText txtUrl;
    TextView lblMessage;

    RSSParser rssParser = new RSSParser();

    RSSFeed rssFeed;

    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_site);

        // buttons
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        txtUrl = (EditText) findViewById(R.id.txtUrl);
        lblMessage = (TextView) findViewById(R.id.lblMessage);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = txtUrl.getText().toString();

                // Validation url
                Log.d("URL Length", "" + url + url.length());
                // check if user entered any data in EditText
                if (url.length() > 0) {
                    lblMessage.setText("");
                    String urlPattern = "^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
                    if (url.matches(urlPattern)) {
                        // valid url
                        new loadRSSFeed().execute(url);
                    } else {
                        // URL not valid
                        lblMessage.setText("Please enter a valid url");
                    }
                } else {
                    // Please enter url
                    lblMessage.setText("Please enter website url");
                }
            }
        });

        // Cancel button click event
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Background Async Task to get RSS data from URL
     * */
    class loadRSSFeed extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNewSiteActivity.this);
            pDialog.setMessage("Fetching RSS Information ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            rssFeed = rssParser.getRSSFeed(url);
            Log.d("rssFeed", " "+ rssFeed);

            if (rssFeed != null) {
                Log.e("RSS URL",
                        rssFeed.getTitle() + "" + rssFeed.getLink() + ""
                                + rssFeed.getDescription() + ""
                                + rssFeed.getLanguage());
                RSSDatabaseHandler rssDb = new RSSDatabaseHandler(
                        getApplicationContext());
                WebSite site = new WebSite(rssFeed.getTitle(), rssFeed.getLink(), rssFeed.getRssLink(),
                        rssFeed.getDescription());
                rssDb.addSite(site);
                Intent i = getIntent();
                // send result code 100 to notify about product update
                setResult(100, i);
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblMessage.setText("Rss url not found. Please check the url or try again");
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_site, menu);
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
