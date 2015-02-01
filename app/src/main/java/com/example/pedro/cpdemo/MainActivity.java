package com.example.pedro.cpdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.commons.validator.routines.UrlValidator;

public class MainActivity extends ActionBarActivity implements Button.OnClickListener {

    private EditText urlEditText;
    private EditText countEditText;
    private Button goButton;
    private RequestsFragment requestsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlEditText = (EditText)findViewById(R.id.urlEditText);
        countEditText = (EditText)findViewById(R.id.countEditText);
        goButton = (Button)findViewById(R.id.goButton);
        goButton.setOnClickListener(this);
        requestsFragment = (RequestsFragment) getFragmentManager().findFragmentById(R.id.requestsFragment);
//        requestsListView = (ListView)findViewById(R.id.requestsListView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == goButton) {
            int count = 0;
            try {
                count = Integer.valueOf(countEditText.getText().toString());
            }
            catch (NumberFormatException e) {}
            if (count == 0) {
                Toast.makeText(this, "Please enter a number 1 or greater", Toast.LENGTH_SHORT).show();
            }
            else {
                String url = urlEditText.getText().toString();
                String[] schemes = {"http", "https"};
                UrlValidator urlValidator = new UrlValidator(schemes);
                if (urlValidator.isValid(url)) {
                    requestsFragment.getUrl(url, count);
                } else {
                    Toast.makeText(this, "Please enter a valid http/s URL", Toast.LENGTH_SHORT).show();
                }
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
