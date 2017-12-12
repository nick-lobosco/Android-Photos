package com.album.photos.photos;

import android.content.Context;
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
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    static ArrayList<Album> temp = new ArrayList<Album>();
    ArrayAdapter adapter;
    private String m_Text = "";
    ListView lv;
    int pos;

    public static final String EXTRA_ALBUM = "com.album.photos.photos.ALBUM";
    public static final String EXTRA_ALBUM_POSITION = "com.album.photos.photos.ALBUM_POSITION";

    public static String fileName = "albums.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        temp = new ArrayList<Album>();
        initAlbums();

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


    private void initAlbums()
    {
//        temp.add(new Album("One"));
//        temp.add(new Album("Two"));
//        temp.add(new Album("Three"));
        temp = null;
        if(readFromFile(this) != null) {
            temp = readFromFile(this);
        }
        else{
            temp = new ArrayList<Album>();
            temp.add(new Album("Stock"));
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        saveToFile(this);

    }

    public void saveToFile(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(temp);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Creates an object by reading it from a file
    public static ArrayList<Album> readFromFile(Context context) {
        ArrayList<Album> result = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            result = (ArrayList<Album>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }


    public void openAlbum(View view){

        if(lv.getItemAtPosition(pos) != null) {
            Toast.makeText(this, "Opening album", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DisplayAlbum.class);
            intent.putExtra(EXTRA_ALBUM, (Album) lv.getItemAtPosition(pos));
            intent.putExtra(EXTRA_ALBUM_POSITION, pos);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent,1234);
        }

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
