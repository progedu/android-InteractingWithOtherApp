package com.example.interactingwithotherapps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tel(View view) {
        Uri number = Uri.parse("tel:5551234");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivityIfSafe(callIntent);
    }

    public void map(View view) {
        // Map point based on address
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        // Or map point based on latitude/longitude
        // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivityIfSafe(mapIntent);
    }

    public void web(View view) {
        Uri webpage = Uri.parse("http://www.android.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivityIfSafe(webIntent);
    }

    public void email(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // The intent does not have a URI, so declare the "text/plain" MIME type
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        // You can also attach multiple items by passing an ArrayList of Uris
        startActivityIfSafeByChooser(emailIntent);
    }

    public void cal(View view) {
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 10, 30);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");
        startActivityIfSafe(calendarIntent);
    }

    private void startActivityIfSafe(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        if(isIntentSafe) {
            startActivity(intent);
        }
    }

    private void startActivityIfSafeByChooser(Intent intent) {
        // Always use string resources for UI text.
        // This says something like "Share this photo with"
        String title = getResources().getString(R.string.chooser_title);
        // Create intent to show chooser
        Intent chooser = Intent.createChooser(intent, title);
        startActivityIfSafe(chooser);
    }

    static final int PICK_CONTACT_REQUEST = 1;  // The request code

    public void contact(View view) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }
}