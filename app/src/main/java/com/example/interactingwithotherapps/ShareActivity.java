package com.example.interactingwithotherapps;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        // Get the intent that started this activity
        Intent intent = getIntent();
        Uri data = intent.getData();

        // Figure out what to do based on the intent type
        if (intent.getType().indexOf("image/") != -1) {
            // Handle intents with image data ..
        } else if (intent.getType().equals("text/plain")) {
            TextView textView = (TextView) findViewById(R.id.share_text);
            textView.setText("Sent text: " + intent.getStringExtra(Intent.EXTRA_TEXT));
        }
    }

    public void share(View view) {

    }
}
