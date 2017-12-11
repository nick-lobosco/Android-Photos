package com.album.photos.photos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Home extends AppCompatActivity {

    ArrayList<Album> temp;
    ArrayAdapter adapter;
    private String m_Text = "";
    ListView lv;
    int pos;

    public static final String EXTRA_ALBUM = "com.album.photos.photos.ALBUM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        temp = new ArrayList<Album>();
        temp.add(new Album("One"));
        temp.add(new Album("Two"));
        temp.add(new Album("Three"));

        lv = (ListView) findViewById(R.id.listViewAlbums);
        adapter = new ArrayAdapter<Album>(this,android.R.layout.simple_list_item_1, temp);
        lv.setAdapter(adapter);
        lv.setSelection(0);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                lv.setSelection(position);
                pos = position;
            }
        });
    }

    public ArrayList<Photo> search(View view){

        ArrayList<Photo> matches = new ArrayList<Photo>();
        Iterator<Album> i = temp.iterator();
        while(i.hasNext()){
            Album a = i.next();
            Iterator<Photo> p = a.getPhotos().iterator();
            while(p.hasNext()){
                Photo photo = p.next();
                if(photo.matches(tags))
                    matches.add(photo);
            }
        }
        return matches;
    }
    public void openAlbum(View view){
        String str = lv.getItemAtPosition(pos).toString();
        System.out.println(str);

        Intent intent = new Intent(this, DisplayAlbum.class);
        intent.putExtra(EXTRA_ALBUM, (Album) lv.getItemAtPosition(pos));
        startActivity(intent);

    }
    public void deleteAlbum(View view){
        //if(pos>=0 && pos<temp.size()){
            temp.remove(pos);
            adapter.notifyDataSetChanged();
            lv.setSelection(pos);
            //pos =
    }

    public void createAlbum(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Album");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                temp.add(new Album(m_Text));
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

        //System.out.println("here");
    }

    public void renameAlbum(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rename Album");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                temp.get(pos).changeName(m_Text);
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
}
