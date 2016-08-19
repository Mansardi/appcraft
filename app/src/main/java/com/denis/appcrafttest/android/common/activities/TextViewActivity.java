package com.denis.appcrafttest.android.common.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.denis.appcrafttest.R;

public class TextViewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_textview);
    if (getSupportActionBar() != null)
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("post_text")) {
      Bundle extras = getIntent().getExtras();
      String infoTextView = extras.getString("post_text");
      TextView textView = (TextView) findViewById(R.id.textView);
      textView.setText(infoTextView);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
