package edu.upenn.cis350;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ButtonClicksActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onButtonClick(View view) {
    	TextView v = (TextView)(findViewById(R.id.clickCounter));
    	String current = (String) v.getText();
    	try {
    		int currval = Integer.parseInt(current);
    		currval += 1;
    		current = Integer.toString(currval);
    	} catch (Exception e) {
    		v.setText("ERROR LOL");
    		Context context = getApplicationContext();
        	CharSequence text = e.toString();
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
    	}
    	v.setText(current);
    	/*
    	// For debug: show toast of current value because I made the font size way too big
    	// So you can't even see it if it goes past one line...
    	Context context = getApplicationContext();
    	CharSequence text = current;
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    	*/
    }
}