package com.album.photos.photos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    boolean havePerm = true;

    public static String EXTRA_PHOTO_URI = "PHOTO_URI";
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL = 100;

    public static final String ALBUM_POSITION = "ALBUM_POSITION";
    public static final String PHOTO_POSITION = "PHOTO_POSITION";


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
        adapter = new CustomListAdapter(this, albumPhotos);
        lv.setAdapter(adapter);
        lv.setSelection(0);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lv.setSelection(i);
                pos = i;
            }
        });

        checkPermission();
    }


    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    havePerm = true;

                } else {

                    havePerm = false;
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void addPhoto(View view){
//        checkPermission();
//        Toast.makeText(this, "Launching Photos", Toast.LENGTH_SHORT).show();
        Intent getPhotoIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (getPhotoIntent.resolveActivity(getPackageManager()) != null && havePerm) {
            Toast.makeText(this, "Please Choose Image", Toast.LENGTH_LONG).show();
            getPhotoIntent.setType("image/*");
            startActivityForResult(getPhotoIntent, REQUEST_IMAGE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Uri targetUri = data.getData();

//            Toast.makeText(this, targetUri.toString(), Toast.LENGTH_SHORT).show();
            albumPhotos.add(new Photo(targetUri.toString()));
            adapter.notifyDataSetChanged();
//
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
           // mImageView.setImageBitmap(imageBitmap);
        }
    }

    public void deletePhoto(View view){
        try {
            lv.setSelection(-1);
            albumPhotos.remove(pos);
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){}
    }

    public void viewPhoto(View view){
        try {
            Intent intent = new Intent(this, PhotoDisplay.class);
            intent.putExtra(EXTRA_PHOTO_URI, albumPhotos.get(pos).getPath());
            intent.putExtra(ALBUM_POSITION, albumPos);
            intent.putExtra(PHOTO_POSITION, pos);
            startActivity(intent);
        }
        catch (Exception e){}
    }



    public class CustomListAdapter extends ArrayAdapter<Photo> {

        private final Activity context;
        private final ArrayList<Photo> items;

        public CustomListAdapter(Activity context, ArrayList<Photo> items) {
            super(context, R.layout.mylist, items);
            // TODO Auto-generated constructor stub

            this.context=context;
            this.items = items;
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.mylist, null,true);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView extratxt = (TextView) rowView.findViewById(R.id.Itemname);

            String photoURI = items.get(position).getPath();

            Bitmap bitmap;
            try {
                bitmap = ThumbnailUtils.extractThumbnail(
                        BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(photoURI))),
                        128, 128);
//                    bitmap = MediaStore.Images.Thumbnails.getThumbnail(
//                        getContentResolver(), Uri.parse(photoURI),
//                        MediaStore.Images.Thumbnails.MINI_KIND,
//                        (BitmapFactory.Options) null );


                imageView.setImageBitmap(bitmap);
            }
            catch (Exception e){}

//            imageView.setImageResource(imgid[position]);
            extratxt.setText(photoURI.substring(photoURI.lastIndexOf('/')+1));


            return rowView;

        };
    }



}
