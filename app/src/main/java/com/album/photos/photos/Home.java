package com.album.photos.photos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    ArrayList<String> temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        temp.add("One");
        temp.add("Two");
        temp.add("Three");

        ListView lv = (ListView) findViewById(R.id.listViewAlbums);

    }

    public void openAlbum(View view){

    }
}
