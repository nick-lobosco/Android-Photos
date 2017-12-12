package com.album.photos.photos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;

import static com.album.photos.photos.DisplayAlbum.EXTRA_PHOTO_URI;

public class PhotoDisplay extends AppCompatActivity {

    ArrayList<Tag> tags;
    ArrayAdapter adapter;
    ListView lv;
    int pos;
    String value;
    String photoURI;
    ImageView iv;
    Album album;
    int index;

    public void next(View view){

    }

    public void previous(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);
        Intent intent = this.getIntent();
        photoURI = intent.getStringExtra(EXTRA_PHOTO_URI);

        iv = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(photoURI)));
            iv.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        tags = new ArrayList<Tag>();
        lv = (ListView) findViewById(R.id.tagLV);
        adapter = new ArrayAdapter<Tag>(this,android.R.layout.simple_list_item_1, tags);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                lv.setSelection(position);
                pos = position;
            }
        });
    }

    public void addTag(View view){
        String type = (view.getId()==R.id.button_addLocation ? "Location" : "Person");
        //System.out.println(view.getId()==R.id.button_addLocation);
        //view.getId()
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New " + type);
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                value = input.getText().toString();
                tags.add(new Tag(type, value));
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void deleteTag(View view){
        tags.remove(pos);
        adapter.notifyDataSetChanged();
    }
}