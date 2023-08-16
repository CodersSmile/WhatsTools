package com.whatstools.captionStatusShare;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.whatstools.R;

public class Captionstatus extends AppCompatActivity {
    String name;
    String[] items;
    ListView listViews;
    int position;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captionstatus);
        this.name = getIntent().getStringExtra("image");
        getSupportActionBar().setTitle(this.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.position = getIntent().getIntExtra("P", 0);
        if (this.position == 0) {
            this.items = getResources().getStringArray(R.array.s_best);
        } else if (this.position == 1) {
            this.items = getResources().getStringArray(R.array.s_clever);
        } else if (this.position == 2) {
            this.items = getResources().getStringArray(R.array.s_cool);
        } else if (this.position == 3) {
            this.items = getResources().getStringArray(R.array.s_cute);
        } else if (this.position == 4) {
            this.items = getResources().getStringArray(R.array.s_fitness);
        } else if (this.position == 5) {
            this.items = getResources().getStringArray(R.array.s_funny);
        } else if (this.position == 6) {
            this.items = getResources().getStringArray(R.array.s_life);
        } else if (this.position == 7) {
            this.items = getResources().getStringArray(R.array.s_love);
        } else if (this.position == 8) {
            this.items = getResources().getStringArray(R.array.s_motivation);
        } else if (this.position == 9) {
            this.items = getResources().getStringArray(R.array.s_sad);
        } else if (this.position == 10) {
            this.items = getResources().getStringArray(R.array.s_savage);
        } else if (this.position == 11) {
            this.items = getResources().getStringArray(R.array.s_selfie);
        } else if (this.position == 12) {
            this.items = getResources().getStringArray(R.array.s_song);
        }
        this.listViews = findViewById(R.id.simpleListView);
        this.listViews.setAdapter(new CustomAdapter1(getApplicationContext(), this.items));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
