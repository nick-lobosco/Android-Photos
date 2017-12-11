package com.album.photos.photos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by basis_000 on 12/10/2017.
 */

public class Album implements Serializable{
    String name;
    ArrayList<Photo> photos;
    //ObservableList<Photo> photos;
//    LocalDate earliestDate;
//    LocalDate latestDate;

    public Album(String name){
        this.name = name;
        photos = new ArrayList<Photo>();//FXCollections.observableArrayList();
//        earliestDate = LocalDate.now();
//        latestDate = LocalDate.now();
    }

    public Album(String name, ArrayList<Photo> photos){
        this.name = name;
        this.photos = photos;
//        earliestDate = LocalDate.now();
//        latestDate = LocalDate.now();
    }

    public void addPhoto(Photo p){
        this.photos.add(p);
    }

    public ArrayList<Photo> getPhotos(){
        return photos;
    }
    public String getName(){
        return name;
    }
    public String toString(){
        return this.name;
    }

    public boolean equals(Object o){
        if(!(o instanceof Album))
            return false;
        if(((Album)o).name.equals(name))
            return true;
        return false;
    }

//    public String getDisplayInfo(){
//        return name + " (" + photos.size() + ")\n" + earliestDate.toString() + " - " + latestDate.toString();
//    }

    public void changeName(String newName){
        this.name = newName;
    }

//    public void updateArray(ObservableList<Photo> photos){
//        this.photos.clear();
//        for(Photo p: photos){
//            this.photos.add(p);
//        }
//    }
}
