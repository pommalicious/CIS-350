package edu.upenn.cis350;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TypingGameActivity extends Activity {
    /** Called when the activity is first created. */
	
	private static final int READY_DIALOG = 1;
	private static final int CORRECT_DIALOG = 2;
	private static final int INCORRECT_DIALOG = 3;
	private long starttime;
	private long endtime;
	private double elapsed = -1;
	private double best = -1.00;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        showDialog(READY_DIALOG);
    }
    
    public void submit(View view) {
    	EditText text = (EditText)(findViewById(R.id.input));
    	String input = text.getText().toString();
    	String master = getResources().getString(R.string.typetext1);
    	if (input.equals(master)) {
    		endtime = System.currentTimeMillis();
    		elapsed = (endtime - starttime) / 1000.00;
    		//showToast("HORSE MONKEYS! THAT'S CORRECT! It took you "+elapsed+" seconds.");
    		removeDialog(CORRECT_DIALOG);
    		showDialog(CORRECT_DIALOG);
    	} else {
    		//showToast("ELEPHANT HORSE! THAT'S WRONG!");
    		removeDialog(INCORRECT_DIALOG);
    		showDialog(INCORRECT_DIALOG);
    	}
    }
    
    public void quit(View view) {
    	finish();
    }
    
    // Helper method to show toasts
    private void showToast(String text) {
    	Context context = getApplicationContext();
    	CharSequence t = text;
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(context, t, duration);
    	toast.show();
    }
    
    protected Dialog onCreateDialog(int id) {
    	if (id == READY_DIALOG) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // this is the message to display
	    	builder.setMessage(R.string.ready); 
                // this is the button to display
	    	builder.setPositiveButton(R.string.yes,
	    		new DialogInterface.OnClickListener() {
                           // this is the method to call when the button is clicked 
	    	           public void onClick(DialogInterface dialog, int id) {
	    	        	   starttime = System.currentTimeMillis();
                           // this will hide the dialog
	    	        	   dialog.cancel();
	    	           }
	    	         });
    		return builder.create();
    	} else if (id == CORRECT_DIALOG && elapsed != -1) {
    		String message = "THAT'S CORRECT! It took you "+elapsed+" seconds.";
    		if (best != -1.00) {
    			if (elapsed < best) {
    				message += "You beat you your previous best record of "+ best+" seconds!";
    				best = elapsed;
    			}
    			else { message += " Your best so far: "+best+ " seconds."; }
    		} else {
    			best = elapsed;
    		}
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // this is the message to display
	    	builder.setMessage(message); 
	            // this is the button to display
	    	builder.setPositiveButton(R.string.again,
	    		new DialogInterface.OnClickListener() {
	                       // this is the method to call when the button is clicked 
	    	           public void onClick(DialogInterface dialog, int id) {
	    	        	   EditText text = (EditText)(findViewById(R.id.input));
	    	        	   text.setText("");
	    	        	   starttime = System.currentTimeMillis();
	                       // this will hide the dialog
	    	        	   dialog.cancel();
	    	           }
	    	         });
			return builder.create();
    	} else if (id == INCORRECT_DIALOG) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // this is the message to display
	    	builder.setMessage("GEE-GOLLY WILLICKERS! THAT'S WRONG!"); 
	            // this is the button to display
	    	builder.setPositiveButton(R.string.again,
	    		new DialogInterface.OnClickListener() {
	                       // this is the method to call when the button is clicked 
	    	           public void onClick(DialogInterface dialog, int id) {
	    	        	   EditText text = (EditText)(findViewById(R.id.input));
	    	        	   text.setText("");
	    	        	   starttime = System.currentTimeMillis();
	                       // this will hide the dialog
	    	        	   dialog.cancel();
	    	           }
	    	         });
			return builder.create();
    	} else return null;
    }

    
}