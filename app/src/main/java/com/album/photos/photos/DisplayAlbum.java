package com.album.photos.photos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.album.photos.photos.Home.EXTRA_ALBUM;

public class DisplayAlbum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_album);

        Intent intent = this.getIntent();
        Album dispAlbum = (Album) intent.getSerializableExtra(EXTRA_ALBUM);
        setTitle("Browsing " + dispAlbum.getName());

        TextView t = (TextView) findViewById(R.id.textView);
        t.setText(dispAlbum.getName());



    }
}
