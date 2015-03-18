package com.irvin.activityswitch;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SecondScreenActivity extends ActionBarActivity {
    private TextView txtName, txtEmail;
    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        TextView txtName = (TextView) findViewById(R.id.txtName);
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
        Button btnClose = (Button) findViewById(R.id.btnClose);

        Intent intent = getIntent();
        //Receiving the data
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        Log.e("Second Screen", name + "." + email);

        // Displaying received data
        txtName.setText(name);
        txtEmail.setText(email);

        intent.putExtra("result", "Second Activity is closed.");
        setResult(1, intent);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second_screen, menu);
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
