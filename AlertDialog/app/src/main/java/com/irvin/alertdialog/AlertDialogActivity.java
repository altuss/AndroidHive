package com.irvin.alertdialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class AlertDialogActivity extends ActionBarActivity {
    private Button alert1, alert2, alert3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        alert1 = (Button) findViewById(R.id.button);
        alert2 = (Button) findViewById(R.id.button2);
        alert3 = (Button) findViewById(R.id.button3);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AlertDialogActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Alert Dialog");

        // Setting Dialog Message
        alertDialog.setMessage("Welcome to AndroidHive.info");

        // Setting Icon to Dialog
        alertDialog.setIcon(android.R.drawable.btn_star_big_on);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });

        alert1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        final AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(AlertDialogActivity.this);

        // Setting Dialog Title
        alertDialog2.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want delete this?");

        // Setting Icon to Dialog
        alertDialog2.setIcon(android.R.drawable.ic_delete);

        // Setting Positive "Yes" Button
        alertDialog2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        alert2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Showing Alert Message
                alertDialog2.show();
            }
        });

        final AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(AlertDialogActivity.this);

        // Setting Dialog Title
        alertDialog3.setTitle("Save File...");

        // Setting Dialog Message
        alertDialog3.setMessage("Do you want to save this file?");

        // Setting Icon to Dialog
        alertDialog3.setIcon(android.R.drawable.ic_menu_save);

        // Setting Positive "Yes" Button
        alertDialog3.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed YES button. Write Logic Here
                Toast.makeText(getApplicationContext(), "You clicked on YES",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog3.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed No button. Write Logic Here
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Netural "Cancel" Button
        alertDialog3.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed Cancel button. Write Logic Here
                Toast.makeText(getApplicationContext(), "You clicked on Cancel",
                        Toast.LENGTH_SHORT).show();
            }
        });

        alert3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Showing Alert Message
                alertDialog3.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alert_dialog, menu);
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
