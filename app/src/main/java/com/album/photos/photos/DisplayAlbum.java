package com.album.photos.photos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.album.photos.photos.Home.EXTRA_ALBUM;

public class DisplayAlbum extends AppCompatActivity {
    static final int REQUEST_IMAGE = 1;
    ListView lv;
    ArrayList<Photo> albumPhotos;
    ArrayAdapter adapter;
    Album dispAlbum;
    int pos;
    int albumPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_album);

        Intent intent = this.getIntent();

        albumPos = intent.getIntExtra(Home.EXTRA_ALBUM_POSITION,0);
        dispAlbum = (Album)  Home.temp.get(albumPos);
        setTitle("Browsing " + dispAlbum.getName());
        albumPhotos = Home.temp.get(albumPos).getPhotos();


        lv = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<Photo>(this, android.R.layout.simple_list_item_1, albumPhotos);
        lv.setAdapter(adapter);
        lv.setSelection(0);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lv.setSelection(i);
                pos = i;
            }
        });

//        TextView t = (TextView) findViewById(R.id.textView);
//        t.setText(dispAlbum.getName());

    }

    public void addPhoto(View view){
        Intent getPhotoIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (getPhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(getPhotoIntent, REQUEST_IMAGE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Uri targetUri = data.getData();
//            setTitle(targetUri.toString());

            albumPhotos.add(new Photo(targetUri.toString()));
            adapter.notifyDataSetChanged();

            Bitmap imageBitmap = (Bitmap) extras.get("data");
           // mImageView.setImageBitmap(imageBitmap);
        }
    }

    public void deletePhoto(View view){
        albumPhotos.remove(pos);
        adapter.notifyDataSetChanged();
    }

    public void viewPhoto(View view){
        Intent intent = new Intent(this, PhotoDisplay.class);
        startActivity(intent);
    }



    public class CustomList extends ArrayAdapter<String> {

        private final Activity context;
        private final String[] web;
        private final Integer[] imageId;
        public CustomList(Activity context,
                          String[] web, Integer[] imageId) {
            super(context, R.layout.list_single, web);
            this.context = context;
            this.web = web;
            this.imageId = imageId;

        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText(web[position]);

            imageView.setImageResource(imageId[position]);
            return rowView;
        }
    }



}
