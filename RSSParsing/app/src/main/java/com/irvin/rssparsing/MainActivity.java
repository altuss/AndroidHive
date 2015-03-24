package com.irvin.rssparsing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;


public class MainActivity extends ActionBarActivity {

    private String finalUrl="http://www.klix.ba/rss/naslovnica";
    private XMLParser parser;
    private EditText title,link,description;
    private Button button;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText)findViewById(R.id.editText1);
        link = (EditText)findViewById(R.id.editText2);
        description = (EditText)findViewById(R.id.editText3);

        button = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new loadRSS().execute(finalUrl);
            }
        });
    }

    class loadRSS extends AsyncTask<String,String,String> {

        RSSFeed feed = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Fetching RSS Information ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            parser = new XMLParser();
            feed = parser.ParseXMLAndStore(parser.fetchXML(url));
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            title.setText(feed.getTitle().toString());
            link.setText(feed.getLink().toString());
            if (feed.description != null) {
                description.setText(feed.getDescription().toString());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pDialog.dismiss();
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
}
