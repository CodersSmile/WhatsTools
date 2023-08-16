package com.whatstools.captionStatusShare;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.startapp.android.publish.ads.banner.Banner;
import com.whatstools.Internetconnection;
import com.whatstools.R;

public class Captionitem extends AppCompatActivity {
    String[] logos = new String[]{"Best", "Clever", "Cool", "Cute", "Fitness", "Funny", "Life", "Love", "Motivation", "Sad", "Savage", "Selfie", "Song"};
    GridView simpleGrid;


    //It's called While click on gridview
    private class simpleGridListner implements OnItemClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent = new Intent(Captionitem.this, Captionstatus.class);
            intent.putExtra("image", Captionitem.this.logos[position]);
            intent.putExtra("P", position);
            Captionitem.this.startActivity(intent);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captionitem);
        getSupportActionBar().setTitle("Caption Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!Internetconnection.checkConnection(this)) {
            Banner banner = findViewById(R.id.startAppBanner);
            banner.hideBanner();
        }
        this.simpleGrid = findViewById(R.id.simpleGridView);
        this.simpleGrid.setAdapter(new CustomAdapter(getApplicationContext(), this.logos));
        this.simpleGrid.setOnItemClickListener(new simpleGridListner());
    }


    //Option menu button click
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    //Back press method
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
