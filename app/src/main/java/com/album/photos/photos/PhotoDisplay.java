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
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;

import static com.album.photos.photos.DisplayAlbum.ALBUM_POSITION;
import static com.album.photos.photos.DisplayAlbum.EXTRA_PHOTO_URI;
import static com.album.photos.photos.DisplayAlbum.PHOTO_POSITION;

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

    int albumPos;
    int photoPos;
    Album myAlb;
    ArrayList<Photo> albumPhotos;


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

        albumPos = intent.getIntExtra(ALBUM_POSITION, 0);
        photoPos = intent.getIntExtra(PHOTO_POSITION, 0);
        myAlb = Home.temp.get(albumPos);
        albumPhotos = myAlb.getPhotos();


        tags = albumPhotos.get(photoPos).getTags();
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

    public void nextImage(View view){
        if(photoPos+1 < albumPhotos.size()){
            photoPos+=1;
            setImage(photoPos);

            Toast.makeText(this, ""+photoPos, Toast.LENGTH_SHORT).show();

            tags = albumPhotos.get(photoPos).getTags();
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
    }

    public void prevImage(View view){
        if(photoPos>0){
            photoPos-=1;
            setImage(photoPos);

            Toast.makeText(this, ""+photoPos, Toast.LENGTH_SHORT).show();

            tags = albumPhotos.get(photoPos).getTags();
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

    }

    private void setImage(int position){
        photoURI = albumPhotos.get(position).getPath();

        iv = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(photoURI)));
            iv.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void deleteTag(View view){
        tags.remove(pos);
        adapter.notifyDataSetChanged();
    }
}