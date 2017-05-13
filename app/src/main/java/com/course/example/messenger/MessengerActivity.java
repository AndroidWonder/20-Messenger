/*
 This application uses a Handler and a Messenger for a Service to
 communicate with an Activity. The Activity wraps the Handler in a Messenger
 and sends the Messenger object to the service as an extra on the intent.
 The Messenger is used to send the message which is then received by the 
 Activity's handleMessage() method. A background thread was used to send a message
 every 500ms. This couldn't be done by the Service because sleeping would pause
 the main UI thread. (A no-no)
 */
package com.course.example.messenger;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.content.Intent;


public class MessengerActivity extends Activity {
	
	private TextView text;
	private Button button;

	//create Handler for Activity to receive messages from Service
	Handler handler = new Handler(){
     	public void handleMessage(Message msg){
     	    text.append(msg.arg1 + "\n");
     	}
     };
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        text = (TextView)findViewById(R.id.text);
        button = (Button)findViewById(R.id.button);

		//create intent to start service, create Messenger object and put in intent
		//then start service
        button.setOnClickListener( new OnClickListener() {
        	public void onClick(View v){
        		Intent intent = new Intent(MessengerActivity.this, MessengerService.class);
        		Messenger mess = new Messenger(handler);
        		intent.putExtra("message", mess);
        		startService(intent); 
        	}
        });
        
    }
}