package com.example.pedro.cpdemo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * Main startup activity
 */
public class MainActivity extends ActionBarActivity implements Button.OnClickListener {

    private EditText urlEditText;
    private EditText countEditText;
    private EditText threadsEditText;
    private Button goButton;
    private RequestsFragment requestsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlEditText = (EditText)findViewById(R.id.urlEditText);
        countEditText = (EditText)findViewById(R.id.countEditText);
        threadsEditText = (EditText)findViewById(R.id.threadsEditText);
        goButton = (Button)findViewById(R.id.goButton);
        goButton.setOnClickListener(this);
        requestsFragment = (RequestsFragment) getFragmentManager().findFragmentById(R.id.requestsFragment);
    }

    private final String URL   = "preferences.URL";
    private final String COUNT = "preferences.COUNT";
    private final String THREADS = "preferences.THREADS";

    @Override
    protected void onPause() {
        super.onPause();
        // saved URL and count
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString(URL, urlEditText.getText().toString());
        editor.putString(COUNT, countEditText.getText().toString());
        editor.putString(THREADS, threadsEditText.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // restore URL and count
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        urlEditText.setText(pref.getString(URL, ""));
        countEditText.setText(pref.getString(COUNT, "1"));
        threadsEditText.setText(pref.getString(THREADS, "5"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // don't need menu for this demo
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == goButton) {
            // go pressed, so validate user input before processing
            String url = urlEditText.getText().toString();
            String[] schemes = {"http", "https"};
            UrlValidator urlValidator = new UrlValidator(schemes);
            if (urlValidator.isValid(url)) {
                int count = 0;
                int threadCount = 5;
                try {
                    count = Integer.valueOf(countEditText.getText().toString());
                    threadCount = Integer.valueOf(threadsEditText.getText().toString());
                }
                catch (NumberFormatException e) {}
                if (count == 0) {
                    Toast.makeText(this, "Please enter a count 1 or greater", Toast.LENGTH_SHORT).show();
                }
                else if (threadCount == 0) {
                    Toast.makeText(this, "Please enter thread count 1 or greater", Toast.LENGTH_SHORT).show();
                }
                else {
                    requestsFragment.getUrl(url, count, threadCount);
                }
            } else {
                Toast.makeText(this, "Please enter a valid http/s URL", Toast.LENGTH_SHORT).show();
            }
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
